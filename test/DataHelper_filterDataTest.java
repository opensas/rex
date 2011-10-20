import java.text.DateFormat;
import java.util.Arrays;
import java.util.List;

import lib.DataHelper;
import lib.DataHelper.FilterCondition;
import lib.StringHelper;
import models.TestModel;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class DataHelper_filterDataTest extends UnitTest {

    @Before
    public void setup() {
    	Fixtures.deleteDatabase();
    }
	
    @Test
    public void filterDataTest() throws Exception {
    	List<TestModel> models, models2;
    	FilterCondition c;
    	
    	Object[] params = { "aaa", "ddd" };
    	
    	Fixtures.loadModels("filterDataTest.yml");
    	
    	assertEquals(5, TestModel.count());
    	
    	models = TestModel.find("stringField >= ? and stringfield <= ?", params).fetch();
    	
    	assertEquals(4, models.size());

    	models = TestModel.find("not (stringField >= ? and stringfield <= ?)", params).fetch();
    	
    	assertEquals(1, models.size());
    	
    	models = TestModel.find("stringField in (?, ?)", params).fetch();
    	
    	assertEquals(2, models.size());

    	c = DataHelper.filterCondition(TestModel.class, 
    			StringHelper.toMapString("stringField:bbb..eee,longField:>200,booleanField:<>0"));

    	models = TestModel.find("stringField in (?, ?)", "ddd", "eee").fetch();
    	models2 = TestModel.find( c.condition, c.params).fetch(); 

    	assertEquals(models, models2);

    	c = DataHelper.filterCondition(TestModel.class, 
    			StringHelper.toMapString("booleanField:<>0,dateField:01-02-2011..01-05-2011"));

    	models = TestModel.find("stringField in (?)", "ddd").fetch();
    	models2 = TestModel.find( c.condition, c.params).fetch(); 

    	assertEquals(models, models2);

    	c = DataHelper.filterCondition(TestModel.class, 
    			StringHelper.toMapString("dateField:01-02-2011..01-05-2011"));

    	models = TestModel.find("stringField in (?, ?, ?)", "bbb", "ccc", "ddd").fetch();
    	models2 = TestModel.find( c.condition, c.params).fetch(); 

    	assertEquals(models, models2);
   	
    }
   
}
