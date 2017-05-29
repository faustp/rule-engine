/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrs.murlin.cia.engine;

import alice.tuprolog.Prolog;
import alice.tuprolog.Theory;
import java.util.Iterator;
import org.apache.log4j.Logger;
import wrs.murlin.cia.CiaException;
import wrs.murlin.cia.theory.data.SolveINFO;

/**
 *
 * @author faust
 */
public class CIAEngine implements CIAEngineInterface {

    private StringBuilder theory;
    private final Logger _logger = Logger.getLogger(CIAEngine.class);
    private Prolog engine = new Prolog();

    public CIAEngine(StringBuilder _theory) {
        this.theory = _theory;
    }

    @Override
    public SolveINFO evaluate() throws CiaException {
        _logger.debug("CIA Engine Theory evaluation.");
        SolveINFO solveinfo = new SolveINFO();
        try {
            engine.setTheory(new Theory(theory.toString()));
            solveinfo.setSolnBlock(engine.solve(BLOCK));
            solveinfo.setSolnForward(engine.solve(FORWARD));
            solveinfo.setSolnEmail(engine.solve(EMAIL));
            solveinfo.setSolnLog(engine.solve(LOG));
            engine.solveEnd();
            engine.clearTheory();
        } catch (Exception ex) {
            engine = null;
            throw new CiaException(ex);
        } finally {
            engine = null;
        }
        return solveinfo;
    }
}
