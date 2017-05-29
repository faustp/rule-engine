/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrs.murlin.cia.engine;

import org.apache.log4j.BasicConfigurator;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import wrs.murlin.cia.theory.data.SolveINFO;

/**
 *
 * @author faust
 */
public class testCIAEngine {

    public testCIAEngine() {
    }

    @BeforeClass
    public static void init() {
        BasicConfigurator.resetConfiguration();
        BasicConfigurator.configure();
    }

    /**
     * Test of evaluate method, of class CIAEngine.
     */
    @Test
    public void testCIAEngineSuccessfulBlockSolution() {
        System.out.println("UT-WRS-SE-033 : Test Successful Block Solution");
        SolveINFO solveInfo = new SolveINFO();
        try {
            StringBuilder theory = new StringBuilder();
            theory.append("source_name('unit-test-source-rsr').\n");
            theory.append("ewl_hit(false).\n");
            theory.append("wcs_category(90).\n");
            theory.append("block:-ewl_hit(false),wcs_category(90).\n");
            CIAEngine engine = new CIAEngine(theory);
            solveInfo = engine.evaluate();
            assertTrue(solveInfo.getSolnBlock().isSuccess());
            System.out.println("Actual Theory :\n" + theory.toString());
            System.out.println("Block Solution : " + solveInfo.getSolnBlock().isSuccess());
            System.out.println("*** Passed ");
        } catch (Exception ex) {
            System.out.println(ex.getCause());
            fail(ex.getLocalizedMessage());
        }
    }

    @Test
    public void testCIAEngineUnsuccessfulBlockSolution() {
        System.out.println("UT-WRS-SE-034 : Test Unsuccessful Block Solution");
        SolveINFO solveInfo = new SolveINFO();
        try {
            StringBuilder theory = new StringBuilder();
            theory.append("source_name('unit-test-source-rsr').\n");
            theory.append("ewl_hit(false).\n");
            theory.append("wcs_category(90).\n");
            theory.append("block:-ewl_hit(true),wcs_category(90).\n");
            CIAEngine engine = new CIAEngine(theory);
            solveInfo = engine.evaluate();
            assertFalse(solveInfo.getSolnBlock().isSuccess());
            System.out.println("Actual Theory : \n" + theory.toString());
            System.out.println("Block Solution : " + solveInfo.getSolnForward().isSuccess());
            System.out.println("*** Passed ");
        } catch (Exception ex) {
            System.out.println(ex.getCause());
            fail(ex.getLocalizedMessage());
        }

    }

    @Test
    public void testCIAEngineSuccessfulForwardSolution() {
        System.out.println("UT-WRS-SE-035 : Test Successful Forward Solution");
        SolveINFO solveInfo = new SolveINFO();
        try {
            StringBuilder theory = new StringBuilder();
            theory.append("source_name('unit-test-source-rsr').\n");
            theory.append("ewl_hit(false).\n");
            theory.append("wcs_category(90).\n");
            theory.append("block:-ewl_hit(true),wcs_category(90).\n");
            theory.append("forward:-not(block),ewl_hit(false).\n");
            CIAEngine engine = new CIAEngine(theory);
            solveInfo = engine.evaluate();
            assertTrue(solveInfo.getSolnForward().isSuccess());
            System.out.println("Actual Theory :\n" + theory.toString());
            System.out.println("Forward Solution : " + solveInfo.getSolnForward().isSuccess());
            System.out.println("*** Passed ");
        } catch (Exception ex) {
            System.out.println(ex.getCause());
            fail(ex.getLocalizedMessage());
        }
    }

    @Test
    public void testCIAEngineUnsuccessfulForwardSolution() {
        System.out.println("UT-WRS-SE-036 : Test Unsuccessful Forward Solution");
        SolveINFO solveInfo = new SolveINFO();
        try {
            StringBuilder theory = new StringBuilder();
            theory.append("source_name('unit-test-source-rsr').\n");
            theory.append("ewl_hit(false).\n");
            theory.append("wcs_category(90).\n");
            theory.append("block:-ewl_hit(true),wcs_category(90).\n");
            theory.append("forward:-block,ewl_hit(true).\n");
            CIAEngine engine = new CIAEngine(theory);
            solveInfo = engine.evaluate();
            assertFalse(solveInfo.getSolnForward().isSuccess());
            System.out.println("Actual Theory :\n" + theory.toString());
            System.out.println("Forward Solution : " + solveInfo.getSolnForward().isSuccess());
            System.out.println("*** Passed ");
        } catch (Exception ex) {
            System.out.println(ex.getCause());
            fail(ex.getLocalizedMessage());
        }
    }

    @Test
    public void testCIAEngineSuccessfulEmailSolution() {
        System.out.println("UT-WRS-SE-037 : Test Successful Email Solution");
        SolveINFO solveInfo = new SolveINFO();
        try {
            StringBuilder theory = new StringBuilder();
            theory.append("source_name('unit-test-source-rsr').\n");
            theory.append("ewl_hit(false).\n");
            theory.append("wcs_category(90).\n");
            theory.append("block:-ewl_hit(true),wcs_category(90).\n");
            theory.append("forward:-block,ewl_hit(true).\n");
            theory.append("email:-not(forward),not(block).\n");
            CIAEngine engine = new CIAEngine(theory);
            solveInfo = engine.evaluate();
            assertTrue(solveInfo.getSolnEmail().isSuccess());
            System.out.println("Actual Theory :\n" + theory.toString());
            System.out.println("Email Solution : " + solveInfo.getSolnEmail().isSuccess());
            System.out.println("*** Passed ");
        } catch (Exception ex) {
            System.out.println(ex.getCause());
            fail(ex.getLocalizedMessage());
        }
    }

    @Test
    public void testCIAEngineUnsuccessfulEmailSolution() {
        System.out.println("UT-WRS-SE-038 : Test Unsuccessful Email Solution");
        SolveINFO solveInfo = new SolveINFO();
        try {
            StringBuilder theory = new StringBuilder();
            theory.append("source_name('unit-test-source-rsr').\n");
            theory.append("ewl_hit(false).\n");
            theory.append("wcs_category(90).\n");
            theory.append("block:-ewl_hit(true),wcs_category(90).\n");
            theory.append("forward:-block,ewl_hit(true).\n");
            theory.append("email:-block;forward.\n");
            CIAEngine engine = new CIAEngine(theory);
            solveInfo = engine.evaluate();
            assertFalse(solveInfo.getSolnEmail().isSuccess());
            System.out.println("Actual Theory :\n" + theory.toString());
            System.out.println("Email Solution : " + solveInfo.getSolnEmail().isSuccess());
            System.out.println("*** Passed ");
        } catch (Exception ex) {
            System.out.println(ex.getCause());
            fail(ex.getLocalizedMessage());
        }
    }

    @Test
    public void testCIAEngineSuccessfulLogSolution() {
        System.out.println("UT-WRS-SE-039 : Test Successful Log Solution");
        SolveINFO solveInfo = new SolveINFO();
        try {
            StringBuilder theory = new StringBuilder();
            theory.append("source_name('unit-test-source-rsr').\n");
            theory.append("ewl_hit(false).\n");
            theory.append("wcs_category(90).\n");
            theory.append("wcs_score_type('unrated').\n");
            theory.append("block:-ewl_hit(true),wcs_category(90).\n");
            theory.append("forward:-block,ewl_hit(true).\n");
            theory.append("email:-forward,block.\n");
            theory.append("log:-not(forward),not(block),not(email),wcs_score_type('unrated').\n");
            CIAEngine engine = new CIAEngine(theory);
            solveInfo = engine.evaluate();
            assertTrue(solveInfo.getSolnLog().isSuccess());
            System.out.println("Actual Theory :\n" + theory.toString());
            System.out.println("Log SolutionInfo : " + solveInfo.getSolnLog().isSuccess());
            System.out.println("*** Passed ");
        } catch (Exception ex) {
            System.out.println(ex.getCause());
            fail(ex.getLocalizedMessage());
        }
    }

    @Test
    public void testCIAEngineUnsuccessfulLogSolution() {
        System.out.println("UT-WRS-SE-040 : Test Unsuccessful Log Solution");
        SolveINFO solveInfo = new SolveINFO();
        try {
            StringBuilder theory = new StringBuilder();
            theory.append("source_name('unit-test-source-rsr').\n");
            theory.append("ewl_hit(false).\n");
            theory.append("wcs_category(90).\n");
            theory.append("wcs_score_type('unrated').\n");
            theory.append("block:-source_name('unit-test-source-rsr'),ewl_hit(true).\n");
            theory.append("forward:-block,not(wcs_score_type('unrated')).\n");
            theory.append("email:-forward,block.\n");
            theory.append("log:-forward,block,email,not(wcs_score_type('unrated')).\n");
            CIAEngine engine = new CIAEngine(theory);
            solveInfo = engine.evaluate();
            assertFalse(solveInfo.getSolnLog().isSuccess());
            System.out.println("Actual Theory :\n" + theory.toString());
            System.out.println("Log SolutionInfo : " + solveInfo.getSolnLog().isSuccess());
            System.out.println("*** Passed");
        } catch (Exception ex) {
            System.out.println(ex.getCause());
            fail(ex.getLocalizedMessage());
        }
    }
}
