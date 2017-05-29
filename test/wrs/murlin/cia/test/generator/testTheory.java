/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrs.murlin.cia.test.generator;

import org.apache.log4j.BasicConfigurator;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;
import wrs.common.murlin.GenericMURLinJSONObject;
import wrs.murlin.cia.CiaException;
import wrs.murlin.cia.generator.GenerateFacts;
import wrs.murlin.cia.generator.GenerateRules;
import wrs.murlin.cia.test.data.DataGenerator;
import wrs.murlin.cia.theory.Theory;
import wrs.murlin.cia.theory.TheoryBuilder;
import wrs.murlin.cia.theory.data.Facts;

/**
 *
 * @author roelr
 */
public class testTheory {

    private final static String COMPONENT_NAME = "cia";
    private final static String SOURCE_NAME = "unit-test-source-" + COMPONENT_NAME;
    private static GenericMURLinJSONObject murlinObj = new GenericMURLinJSONObject();
    private static DataGenerator dataGen = new DataGenerator();

    @BeforeClass
    public static void init() {
        BasicConfigurator.resetConfiguration();
        BasicConfigurator.configure();
        murlinObj = generateTestMURLinObject("roel.com");
        murlinObj.getCategory().getWcs().add("90");
        murlinObj.getCategory().setScore("71");
    }

    @Test
    public void testBuild() {
        System.out.println("* * * Test BuildTheory with valid Facts and Rules");
        Theory theory = new Theory();
        boolean ok = false;
        Facts facts = new Facts();
        murlinObj.getDyn_conf().put("cia", "{\"rule\":{\"block\":\"wcs_category(90)\"}}");
        try {
            facts = (new GenerateFacts(murlinObj).generate());
            theory.setFacts(facts);
            theory.setRules(new GenerateRules(murlinObj).generate());
            StringBuilder builtTheory = new TheoryBuilder(theory).build();
            System.out.println(builtTheory);
            ok = true;
        } catch (CiaException ex) {
            System.out.println(ex.getCause());
            fail(ex.getLocalizedMessage());
        }
        if (ok) {
            assertTrue(true);
            System.out.println("Build Theory was successfully generated");
            System.out.println("*** Passed");
        }
    }

    @Test
    public void testInvalidBuild() {
        System.out.println("UT-WRS-SE-045 : Test BuildTheory with invalid Facts and Rules");
        Theory theory = new Theory();
        boolean ok = false;
        Facts facts = new Facts();
        murlinObj.getDyn_conf().put("cia", "{\"rule\":{\"block\":\"wcs_category(\"90\")\"}}");
        try {
            facts = (new GenerateFacts(murlinObj).generate());
            theory.setFacts(facts);
            theory.setRules(new GenerateRules(murlinObj).generate());
            StringBuilder builtTheory = new TheoryBuilder(theory).build();
            System.out.println(builtTheory);
        } catch (CiaException ex) {
            ok = true;
            assertTrue(true);
            System.out.println("Exception error has successfully handled :: " + ex);
            System.out.println("*** Passed");
        }
        if (!ok) {
            fail("Process should throw CiaException error");
        }
    }

    private static GenericMURLinJSONObject generateTestMURLinObject(String url) {
        return dataGen.generateMURLinJSONObject(SOURCE_NAME, dataGen.generateURL(url));
    }
}
