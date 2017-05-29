/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrs.murlin.cia.test.generator;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;
import wrs.common.murlin.GenericMURLinJSONObject;
import wrs.murlin.cia.CiaException;
import wrs.murlin.cia.generator.GenerateRules;
import wrs.murlin.cia.test.data.DataGenerator;

/**
 *
 * @author faust
 */
public class testRuleset {

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

    //@Test
    public void testGenerateRule() throws Exception {
        System.out.println("UT-WRS-SE-044 : Test CIA Rule generation");
        murlinObj.getDyn_conf().put("cia", "{\"rule\":{\"block\":\"ewl.hit(false),wcs.category(90)\",\"email\":\"block\",\"forward\":\"not(block),(not(wcs.category(79)),not(wcs.category(75)),not(wcs.category(39)),not(wcs.category(76)))\"},\"channel\":{\"forward\":[\"com.trendmicro.spn.wtp.stag.murlin.rf.relay.in\"]}}");
        GenerateRules rule = new GenerateRules(murlinObj);
        org.junit.Assert.assertEquals("CIARules{block=block:-ewl.hit(false),wcs.category(90), forward=forward:-not(block),(not(wcs.category(79)),not(wcs.category(75)),not(wcs.category(39)),not(wcs.category(76))), email=email:-block, log=null}", rule.generate().toString());
        System.out.println("*** Passed : CIA Rule was properly generated");
    }

    //@Test
    public void testInvalidBlockRule1() throws Exception {
        System.out.println("UT-WRS-SE-044 : Test CIA Rule generation [Invalid Block Rule]");
        murlinObj.getDyn_conf().put("cia", "{\"rule\":{\"block\":\"not(block)\",\"email\":\"block\",\"forward\":\"not(block),(not(wcs.category(79)),not(wcs.category(75)),not(wcs.category(39)),not(wcs.category(76)))\"},\"channel\":{\"forward\":[\"com.trendmicro.spn.wtp.stag.murlin.rf.relay.in\"]}}");
        GenerateRules rule = new GenerateRules(murlinObj);
        try {
            rule.generate();
            org.junit.Assert.fail("An exception should be thrown for invalid RULE on block");
        } catch (CiaException cex) {
            System.out.println(cex.getCause());
            org.junit.Assert.assertEquals("Block rule can't have block or not(block) operator... never ending loop will be triggered", cex.getCause().getMessage());
            System.out.println("*** Passed");
        }
    }

    //@Test
    public void testInvalidBlockRule2() throws Exception {
        System.out.println("UT-WRS-SE-044 : Test CIA Rule generation [Invalid Block Rule]");
        murlinObj.getDyn_conf().put("cia", "{\"rule\":{\"block\":\"block,wcs_category(90)\",\"email\":\"block\",\"forward\":\"not(block),(not(wcs.category(79)),not(wcs.category(75)),not(wcs.category(39)),not(wcs.category(76)))\"},\"channel\":{\"forward\":[\"com.trendmicro.spn.wtp.stag.murlin.rf.relay.in\"]}}");
        GenerateRules rule = new GenerateRules(murlinObj);
        try {
            rule.generate();
            org.junit.Assert.fail("An exception should be thrown for invalid RULE on block");
        } catch (CiaException cex) {
            System.out.println(cex.getCause());
            org.junit.Assert.assertEquals("Block rule can't have block or not(block) operator... never ending loop will be triggered", cex.getCause().getMessage());
            System.out.println("*** Passed");
        }
    }

    //@Test
    public void testInvalidForwardRule1() throws Exception {
        System.out.println("UT-WRS-SE-044 : Test CIA Rule generation [Invalid Forward Rule]");
        murlinObj.getDyn_conf().put("cia", "{\"rule\":{\"block\":\"wcs_category(93)\",\"email\":\"wcs_category(79)\",\"forward\":\"forward\"},\"channel\":{\"forward\":[\"com.trendmicro.spn.wtp.stag.murlin.rf.relay.in\"]}}");
        GenerateRules rule = new GenerateRules(murlinObj);
        try {
            rule.generate();
            org.junit.Assert.fail("An exception should be thrown for invalid RULE on forward");
        } catch (CiaException cex) {
            System.out.println(cex.getCause());
            org.junit.Assert.assertEquals("Forward rule can't have forward or not(forward) operator... never ending loop will be triggered", cex.getCause().getMessage());
            System.out.println("*** Passed");
        }
    }

    //@Test
    public void testInvalidForwardRule2() throws Exception {
        System.out.println("UT-WRS-SE-044 : Test CIA Rule generation [Invalid Forward Rule]");
        murlinObj.getDyn_conf().put("cia", "{\"rule\":{\"block\":\"wcs_category(93)\",\"email\":\"wcs_category(79)\",\"forward\":\"not(forward),wcs_category(90)\"},\"channel\":{\"forward\":[\"com.trendmicro.spn.wtp.stag.murlin.rf.relay.in\"]}}");
        GenerateRules rule = new GenerateRules(murlinObj);
        try {
            rule.generate();
            org.junit.Assert.fail("An exception should be thrown for invalid RULE on forward");
        } catch (CiaException cex) {
            System.out.println(cex.getCause());
            org.junit.Assert.assertEquals("Forward rule can't have forward or not(forward) operator... never ending loop will be triggered", cex.getCause().getMessage());
            System.out.println("*** Passed");
        }
    }

    @Test
    public void testInvalidEmailRule1() throws Exception {
        System.out.println("UT-WRS-SE-044 : Test CIA Rule generation [Invalid Email Rule]");
        murlinObj.getDyn_conf().put("cia", "{\"rule\":{\"block\":\"wcs_category(93)\",\"email\":\"email\",\"forward\":\"wcs_category(79)\"},\"channel\":{\"forward\":[\"com.trendmicro.spn.wtp.stag.murlin.rf.relay.in\"]}}");
        GenerateRules rule = new GenerateRules(murlinObj);
        try {
            rule.generate();
            org.junit.Assert.fail("An exception should be thrown for invalid RULE on email");
        } catch (CiaException cex) {
            System.out.println(cex.getCause());
            org.junit.Assert.assertEquals("Email rule can't have email or not(email) operator... never ending loop will be triggered", cex.getCause().getMessage());
            System.out.println("*** Passed");
        }
    }

    @Test
    public void testInvalidEmailRule2() throws Exception {
        System.out.println("UT-WRS-SE-044 : Test CIA Rule generation [Invalid Email Rule]");
        murlinObj.getDyn_conf().put("cia", "{\"rule\":{\"block\":\"wcs_category(93)\",\"email\":\"not(email)\",\"forward\":\"wcs_category(90)\"},\"channel\":{\"forward\":[\"com.trendmicro.spn.wtp.stag.murlin.rf.relay.in\"]}}");
        GenerateRules rule = new GenerateRules(murlinObj);
        try {
            rule.generate();
            org.junit.Assert.fail("An exception should be thrown for invalid RULE on email");
        } catch (CiaException cex) {
            System.out.println(cex.getCause());
            org.junit.Assert.assertEquals("Email rule can't have email or not(email) operator... never ending loop will be triggered", cex.getCause().getMessage());
            System.out.println("*** Passed");
        }
    }

    private static GenericMURLinJSONObject generateTestMURLinObject(String url) {
        return dataGen.generateMURLinJSONObject(SOURCE_NAME, dataGen.generateURL(url));
    }
}
