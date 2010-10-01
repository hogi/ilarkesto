package ilarkesto.concurrent;

import java.util.ArrayList;
import java.util.List;

public final class BatchTask extends ATask {

	private List<TaskWrapper> tasks = new ArrayList<TaskWrapper>();
	private TaskWrapper currentTask;
	private float progressed;

	@Override
	protected void perform() {
		int totalWeight = 0;
		for (TaskWrapper wrapper : tasks) {
			totalWeight += wrapper.weight;
		}
		for (TaskWrapper wrapper : tasks) {
			wrapper.effectiveWeight = (float) wrapper.weight / (float) totalWeight;
		}

		while (!tasks.isEmpty() && !isAbortRequested()) {
			currentTask = tasks.get(0);
			tasks.remove(currentTask);
			currentTask.task.run();
			progressed += currentTask.effectiveWeight;
		}
	}

	@Override
	public void abort() {
		if (currentTask != null) currentTask.task.abort();
		super.abort();
	}

	@Override
	public void reset() {
		for (TaskWrapper taskWrapper : tasks) {
			if (taskWrapper.task.isFinished()) continue;
			taskWrapper.task.abort();
		}
		super.reset();
	}

	@Override
	public String getProgressMessage() {
		return currentTask == null ? null : currentTask.task.getProgressMessage();
	}

	@Override
	public float getProgress() {
		if (currentTask == null) return super.getProgress();
		return progressed + (currentTask.effectiveWeight * currentTask.task.getProgress());
	}

	public void addTask(ATask task) {
		addTask(task, 1);
	}

	public void addTask(ATask task, int weight) {
		tasks.add(new TaskWrapper(task, weight));
	}

	private static class TaskWrapper {

		private ATask task;
		private int weight;
		private float effectiveWeight;

		public TaskWrapper(ATask task, int weight) {
			this.task = task;
			this.weight = weight;
		}

	}

}
