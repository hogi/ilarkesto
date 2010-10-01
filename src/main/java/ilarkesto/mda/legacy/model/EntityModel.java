package ilarkesto.mda.legacy.model;

import ilarkesto.base.Str;

import java.util.ArrayList;
import java.util.List;

public final class EntityModel extends DatobModel {

	private List<ActionModel> actions = new ArrayList<ActionModel>();
	private boolean ownable;
	private EntityModel userModel;
	private boolean viewProtected = false;
	private boolean editProtected;
	private List<BackReferenceModel> backReferences = new ArrayList<BackReferenceModel>();

	public EntityModel(String name, String packageName) {
		super(name, packageName);
	}

	public void addBackReference(BackReferenceModel backReference) {
		backReferences.add(backReference);
	}

	public List<BackReferenceModel> getBackReferences() {
		return backReferences;
	}

	public List<ActionModel> getActions() {
		return actions;
	}

	public ActionModel addAction(String name) {
		ActionModel action = new ActionModel(name, getPackageName());
		action.addParameter(Str.lowercaseFirstLetter(getName()), this);
		actions.add(action);
		return action;
	}

	@Override
	public boolean isEntity() {
		return true;
	}

	@Override
	public boolean isValueObject() {
		return false;
	}

	public String getDaoName() {
		return getName() + "Dao";
	}

	public String getAbstractBaseDaoClassName() {
		return getAbstractBaseClassName() + "Dao";
	}

	public final boolean isOwnable() {
		BeanModel superbean = getSuperbean();
		if (superbean != null) {
			if (superbean instanceof EntityModel) {
				if (((EntityModel) superbean).isOwnable()) return true;
			}
		}
		return ownable;
	}

	public final void setOwnable(boolean ownable) {
		this.ownable = ownable;
	}

	public void setUserModel(EntityModel userModel) {
		this.userModel = userModel;
	}

	public EntityModel getUserModel() {
		return userModel;
	}

	public final boolean isViewProtected() {
		BeanModel superbean = getSuperbean();
		if (superbean != null) {
			if (superbean instanceof EntityModel) {
				if (((EntityModel) superbean).isViewProtected()) return true;
			}
		}
		return viewProtected;
	}

	public final void setViewProtected(boolean viewProtected) {
		this.viewProtected = viewProtected;
	}

	public final boolean isEditProtected() {
		BeanModel superbean = getSuperbean();
		if (superbean != null) {
			if (superbean instanceof EntityModel) {
				if (((EntityModel) superbean).isEditProtected()) return true;
			}
		}

		return editProtected;
	}

	public final void setEditProtected(boolean editProtected) {
		this.editProtected = editProtected;
	}

	private boolean deleteProtected;

	public final boolean isDeleteProtected() {
		BeanModel superbean = getSuperbean();
		if (superbean != null) {
			if (superbean instanceof EntityModel) {
				if (((EntityModel) superbean).isDeleteProtected()) return true;
			}
		}

		return deleteProtected;
	}

	public final void setDeleteProtected(boolean deleteProtected) {
		this.deleteProtected = deleteProtected;
	}

}
