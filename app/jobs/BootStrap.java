package jobs;

import models.Variable;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class BootStrap extends Job {
	
	@Override
	public void doJob() {
		if (Variable.count()==0) {
			Fixtures.deleteAllModels();
			Fixtures.loadModels("variable.yml");
		}
	}

}
