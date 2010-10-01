package ilarkesto.persistence;

import ilarkesto.base.time.Date;
import ilarkesto.core.logging.Log;
import ilarkesto.fp.Predicate;
import ilarkesto.io.IO;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FileEntityStore implements EntityStore {

	private static final Log LOG = Log.get(FileEntityStore.class);

	// --- dependencies ---

	private Serializer beanSerializer;

	public void setBeanSerializer(Serializer beanSerializer) {
		this.beanSerializer = beanSerializer;
	}

	private EntityfilePreparator entityfilePreparator;

	public void setEntityfilePreparator(EntityfilePreparator entityfilePreparator) {
		this.entityfilePreparator = entityfilePreparator;
	}

	private String dir;

	public void setDir(String dir) {
		this.dir = dir;
	}

	private String backupDir;

	public void setBackupDir(String backupDir) {
		this.backupDir = backupDir;
	}

	// --- ---

	public synchronized void save(AEntity entity) {
		// entity.setLastModified(DateAndTime.now());

		String alias = aliases.get(entity.getClass());
		File tmpFile = new File(dir + "/tmp/" + entity.getId() + ".xml");

		if (!tmpFile.getParentFile().exists()) {
			tmpFile.getParentFile().mkdirs();
		}

		// save
		BufferedOutputStream out;
		try {
			out = new BufferedOutputStream(new FileOutputStream(tmpFile));
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		beanSerializer.serialize(entity, out);
		try {
			out.close();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		File file = new File(dir + "/" + alias + "/" + entity.getId() + ".xml");

		// backup
		if (file.exists() && !(entity instanceof BackupHostile)) {
			backup(file, entity.getDao().getEntityName());
		}

		IO.move(tmpFile, file, true);

		getDao(entity.getClass()).put(entity.getId(), entity);

		LOG.debug("Entity saved:", entity, "->", file.getPath());
	}

	public synchronized void delete(AEntity entity) {
		String alias = aliases.get(entity.getClass());
		File file = new File(dir + "/" + alias + "/" + entity.getId() + ".xml");

		// backup
		if (file.exists() && !(entity instanceof BackupHostile)) {
			backup(file, entity.getDao().getEntityName());
		}

		// delete
		if (!file.delete() && file.exists())
			throw new RuntimeException("Deleting entity file failed: " + file.getAbsolutePath());

		getDao(entity.getClass()).remove(entity.getId());

		LOG.debug("Entity deleted:", file.getPath(), entity.getClass().getSimpleName(), entity);
	}

	private Map<String, AEntity> getDao(Class<? extends AEntity> type) {
		Map<String, AEntity> dao = data.get(type);
		if (dao == null) { throw new RuntimeException("Unknown entity type: " + type); }
		return dao;
	}

	@Override
	public AEntity getById(String id) {
		for (Map.Entry<Class<AEntity>, Map<String, AEntity>> daoEntry : data.entrySet()) {
			AEntity entity = daoEntry.getValue().get(id);
			if (entity != null) return entity;
		}
		return null;
	}

	public synchronized AEntity getEntity(Predicate<Class> typeFilter, Predicate<AEntity> entityFilter) {
		for (Map.Entry<Class<AEntity>, Map<String, AEntity>> daoEntry : data.entrySet()) {
			if (typeFilter != null && !typeFilter.test(daoEntry.getKey())) continue;
			for (AEntity entity : daoEntry.getValue().values()) {
				if (entityFilter.test(entity)) return entity;
			}
		}
		return null;
	}

	@Override
	public List<AEntity> getByIds(Collection<String> ids) {
		List<AEntity> result = new ArrayList<AEntity>(ids.size());
		for (Map.Entry<Class<AEntity>, Map<String, AEntity>> entry : data.entrySet()) {
			Map<String, AEntity> entites = entry.getValue();
			for (String id : ids) {
				AEntity entity = entites.get(id);
				if (entity != null) result.add(entity);
			}
		}
		return result;
	}

	public synchronized Set<AEntity> getEntities(Predicate<Class> typeFilter, Predicate<AEntity> entityFilter) {
		Set<AEntity> result = new HashSet<AEntity>();
		for (Map.Entry<Class<AEntity>, Map<String, AEntity>> entry : data.entrySet()) {
			if (typeFilter != null && !typeFilter.test(entry.getKey())) continue;
			if (entityFilter == null) {
				result.addAll(entry.getValue().values());
			} else {
				for (AEntity entity : entry.getValue().values()) {
					if (entityFilter.test(entity)) result.add(entity);
				}
			}
		}
		return result;
	}

	public synchronized int getEntitiesCount(Predicate<Class> typeFilter, Predicate<AEntity> entityFilter) {
		int result = 0;
		for (Map.Entry<Class<AEntity>, Map<String, AEntity>> entry : data.entrySet()) {
			if (typeFilter != null && !typeFilter.test(entry.getKey())) continue;
			if (entityFilter == null) {
				result += entry.getValue().size();
			} else {
				for (AEntity entity : entry.getValue().values()) {
					if (entityFilter.test(entity)) result++;
				}
			}
		}
		return result;
	}

	private Map<Class, String> aliases = new HashMap<Class, String>();

	private Map<Class<AEntity>, Map<String, AEntity>> data = new HashMap<Class<AEntity>, Map<String, AEntity>>();

	public void setAlias(String alias, Class cls) {
		aliases.put(cls, alias);
		beanSerializer.setAlias(alias, cls);
	}

	public void load(Class<? extends AEntity> cls, String alias) {
		aliases.put(cls, alias);

		Map<String, AEntity> entities = new HashMap<String, AEntity>();
		data.put((Class<AEntity>) cls, entities);

		beanSerializer.setAlias(alias, cls);

		File f = new File(dir + "/" + alias);
		LOG.info("Loading entities:", alias);
		// if (!f.exists()) {
		// LOG.warn("Store directory does not exist. creating:", dir);
		// if (!f.mkdirs()) throw new RuntimeException("Creating store directory failed: " + dir);
		// }
		int count = 0;
		File[] files = f.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				try {
					if (loadObject(files[i], entities, cls, alias)) count++;
				} catch (Throwable ex) {
					throw new RuntimeException("Loading object from " + files[i] + " failed", ex);
				}
			}
		}
		// LOG.info(" Loaded entities:", alias, count);
	}

	private boolean loadObject(File file, Map<String, AEntity> entities, Class type, String alias) {
		String name = file.getName();
		if (!name.endsWith(".xml")) {
			LOG.warn("Unsupported file. Skipping:", name);
			return false;
		}

		if (entityfilePreparator != null) entityfilePreparator.prepareEntityfile(file, type, alias);

		BufferedInputStream in;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
		} catch (FileNotFoundException ex) {
			throw new RuntimeException(ex);
		}
		AEntity entity = (AEntity) beanSerializer.deserialize(in);
		entities.put(entity.getId(), entity);
		try {
			in.close();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		return true;
	}

	private void backup(File src, String type) {
		if (src.isDirectory()) throw new RuntimeException("sorry, backing up directories is not implemented yet.");

		String destinationPath = backupDir + "/" + Date.today() + "/" + type + "/";
		File dst = new File(destinationPath + src.getName());
		for (int i = 2; dst.exists(); i++) {
			dst = new File(destinationPath + i + "_" + src.getName());
		}

		// LOG.debug("Backing up", src.getPath(), "to", dst.getPath());
		IO.copyFile(src.getPath(), dst.getPath());
	}

}
