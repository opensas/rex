import lib.TemplateHelper;

import org.junit.Test;

import play.test.UnitTest;


public class TemplateHelper_FormButtonTest extends UnitTest {

	
	@Test
	public void buttonLabelTest() {
		
		assertEquals("no accesskey", 
				"label",
				TemplateHelper.getButtonLabel("label"));

		assertEquals("no accesskey", 
				"",
				TemplateHelper.getButtonAccessKey("label"));

		assertEquals("first character as accesskey", 
				"<u>l</u>abel",
				TemplateHelper.getButtonLabel("&label"));

		assertEquals("first character as accesskey", 
				"l",
				TemplateHelper.getButtonAccessKey("&label"));

		assertEquals("middle character as accesskey", 
				"la<u>b</u>el",
				TemplateHelper.getButtonLabel("la&bel"));

		assertEquals("middle character as accesskey", 
				"b",
				TemplateHelper.getButtonAccessKey("la&bel"));

		assertEquals("last character as accesskey", 
				"label<u>s</u>",
				TemplateHelper.getButtonLabel("label&s"));

		assertEquals("last character as accesskey", 
				"s",
				TemplateHelper.getButtonAccessKey("label&s"));

		assertEquals("past last character as accesskey", 
				"labels",
				TemplateHelper.getButtonLabel("labels&"));

		assertEquals("past last character as accesskey", 
				"",
				TemplateHelper.getButtonAccessKey("labels&"));

		assertEquals("duplicate indicators", 
				"l<u>a</u>b&els&",
				TemplateHelper.getButtonLabel("l&ab&els&"));

		assertEquals("duplicate indicators", 
				"a",
				TemplateHelper.getButtonAccessKey("l&ab&els&"));
		
	}
}
