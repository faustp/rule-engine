/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrs.murlin.cia.generator.impl;

import wrs.murlin.cia.CiaException;

/**
 *
 * @author faust
 */
public interface CIAGeneratorInterface {

    /*
     * Facts Constant Character
     */
    public final String ENCLOSE = "'";
    public final String OPEN_TERM = "(";
    public final String CLOSE_TERM = ")";
    /*
     * Default Facts Term
     */
    public final String SOURCE_NAME = "source_name";
    public final String URL_DESC = "url_description";
    public final String RESOLVED_IP = "resolve_ip";
    public final String ALPS_HIT_CNT = "alps_hit_cnt";
    public final String EWL_HIT = "ewl_hit";
    public final String WCS_CAT = "wcs_category";
    public final String WCS_SCORE = "wcs_score";
    public final String WCS_SCORE_TYPE = "wcs_score_type";
    public final String WCS_CASCADE = "wcs_cascade";
    public final String CREATOR = "creator";
    public final String ORI_SRC = "ori_src";
    public final String INPUT_SRC = "input_src";
    /*
     * Added by roel
     * New Default Facts
     */
    public final String MALICIOUS = "malicious";
    public final String NORMAL = "normal";
    public final String NEW_URL = "new_url";
    public final String NEGATIVE = "negative";
    public final String CASCADE_DIR = "cascade_dir";
    public final String CASCADE_DOMAIN = "cascade_domain";
    public final String FILE_SIZE = "file_size";
    
    public Object generate() throws CiaException;

    public String constructFacts(String term, String termVal) throws CiaException;

    public String getValue(String[] keyRef, String jsonString) throws CiaException;
}
