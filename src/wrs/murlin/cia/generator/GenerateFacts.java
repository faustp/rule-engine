/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrs.murlin.cia.generator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import wrs.common.DefaultWRSConstants;
import wrs.common.murlin.GenericMURLinJSONObject;
import wrs.murlin.cia.CiaException;
import wrs.murlin.cia.datatype.checker.DataTypeChecker;
import wrs.murlin.cia.generator.impl.CIAGeneratorImpl;
import wrs.murlin.cia.theory.data.Facts;

/**
 *
 * @author faust
 */
public class GenerateFacts extends CIAGeneratorImpl {

    private GenericMURLinJSONObject _murlinObj;
    private final Logger _logger = Logger.getLogger(GenerateFacts.class);

    public GenerateFacts(GenericMURLinJSONObject murlinObj) {
        this._murlinObj = murlinObj;
    }

    @Override
    public Facts generate() throws CiaException {
        Facts facts = new Facts();
        try {
            facts.setDefaultFacts(generateDefaultFacts());
            facts.setOptionalFacts(generateOptionalFacts());
        } catch (Exception ex) {
            throw new CiaException(ex);
        }
        return facts;
    }

    private ArrayList<String> generateDefaultFacts() {
        ArrayList<String> _defaultFacts = new ArrayList<String>();
        String source_name = (_murlinObj.getSrcCHName() == null || _murlinObj.getSrcCHName().length() == 0) ? null : _murlinObj.getSrcCHName();
        String raw_description = (_murlinObj.getUrl().getRaw_description() == null || _murlinObj.getUrl().getRaw_description().length() == 0) ? null : _murlinObj.getUrl().getRaw_description();
        String resolve_ip = (_murlinObj.getUrl().getResolved_ip() == null || _murlinObj.getUrl().getResolved_ip().length() == 0) ? null : _murlinObj.getUrl().getResolved_ip();
        String alps_hit_cnt = (_murlinObj.getALPShitcnt() == null || _murlinObj.getALPShitcnt().length() == 0) ? null : _murlinObj.getALPShitcnt();
        String creator = (_murlinObj.getDbh().getCreator() == null || _murlinObj.getDbh().getCreator().length() == 0) ? null : _murlinObj.getDbh().getCreator();
        String ori_src = (_murlinObj.getDbh().getOri_src() == null || _murlinObj.getDbh().getOri_src().length() == 0) ? null : _murlinObj.getDbh().getOri_src();
        String input_source = (_murlinObj.getDbh().getInput_source() == null || _murlinObj.getDbh().getInput_source().length() == 0) ? null : _murlinObj.getDbh().getInput_source();
        String score = (_murlinObj.getCategory().getScore() == null || _murlinObj.getCategory().getScore().length() == 0) ? null : _murlinObj.getCategory().getScore();
        boolean malicious = _murlinObj.isMalicious();
        boolean normal = _murlinObj.isNormal();
        boolean new_url = _murlinObj.isNewDomain();
        boolean negative = _murlinObj.isNegative();
        boolean cascade_dir = _murlinObj.isDirCascaded();
        boolean cascade_domain = _murlinObj.isDomainCascaded();
        boolean wcs_cascade = _murlinObj.isCascaded();
        boolean _ewlResult = _murlinObj.isWhiteListed();
        String file_size = "0";
        if (_murlinObj.getFileInfo() != null) {
            file_size = String.valueOf(_murlinObj.getFileInfo().getFilesize());
            _defaultFacts.add(constructFacts(FILE_SIZE, file_size));
        }
        ArrayList<String> wcs_category = (_murlinObj.getCategory().getWcs() == null || _murlinObj.getCategory().getWcs().isEmpty()) ? null : _murlinObj.getCategory().getWcs();
        if (source_name != null) {
            _defaultFacts.add(constructFacts(SOURCE_NAME, source_name));
        }
        if (raw_description != null) {
            _defaultFacts.add(constructFacts(URL_DESC, raw_description));
        }
        if (resolve_ip != null) {
            _defaultFacts.add(constructFacts(RESOLVED_IP, resolve_ip));
        }
        if (alps_hit_cnt != null) {
            _defaultFacts.add(constructFacts(ALPS_HIT_CNT, alps_hit_cnt));
        }
        if (creator != null) {
            _defaultFacts.add(constructFacts(CREATOR, creator));
        }
        if (ori_src != null) {
            _defaultFacts.add(constructFacts(ORI_SRC, ori_src));
        }
        if (input_source != null) {
            _defaultFacts.add(constructFacts(INPUT_SRC, input_source));
        }
        if (score != null) {
            _defaultFacts.add(constructFacts(WCS_SCORE, score));
        }
        /*
         * Set WCS cascade
         */
        _defaultFacts.add(constructFacts(WCS_CASCADE, String.valueOf(wcs_cascade)));
        _defaultFacts.add(constructFacts(CASCADE_DIR, String.valueOf(cascade_dir)));
        _defaultFacts.add(constructFacts(CASCADE_DOMAIN, String.valueOf(cascade_domain)));
        /*
         * Set EWL
         */
        _defaultFacts.add(constructFacts(EWL_HIT, String.valueOf(_ewlResult)));
        /*
         * Set Category Type
         */
        _defaultFacts.add(constructFacts(MALICIOUS, String.valueOf(malicious)));
        _defaultFacts.add(constructFacts(NORMAL, String.valueOf(normal)));
        _defaultFacts.add(constructFacts(NEW_URL, String.valueOf(new_url)));
        _defaultFacts.add(constructFacts(NEGATIVE, String.valueOf(negative)));
        /*
         * Set WCS categories
         */
        if (wcs_category != null) {
            if (!wcs_category.isEmpty()) {
                for (String category : wcs_category) {
                    if (category != null) {
                        _defaultFacts.add(constructFacts(WCS_CAT, category));
                    }
                }
                _defaultFacts.add(constructFacts(WCS_SCORE_TYPE, getScoreType(wcs_category)));
            }
        }
        wcs_category = null;
        /*
         * Set WCS score type
         */
        _logger.debug("Generated Default Facts :: " + _defaultFacts);
        return _defaultFacts;
    }

    private ArrayList<String> generateOptionalFacts() throws CiaException {
        String jsonReqFields = _murlinObj.getDyn_conf().get("result_fields") == null ? "" : _murlinObj.getDyn_conf().get("result_fields");
        ArrayList<String> optionalFacts = new ArrayList<String>();
        try {
            if (!jsonReqFields.equalsIgnoreCase("") || jsonReqFields.length() > 0) {
                JsonArray jsonarray = (JsonArray) ((JsonObject) new JsonParser().parse(jsonReqFields)).get("fields");
                _logger.debug("Fields to be generated from the MURLin result" + jsonReqFields);
                if (jsonarray.size() > 0) {
                    HashMap<String, String> factsTerm = new HashMap<String, String>();
                    ArrayList<String> fields = new ArrayList<String>();
                    for (JsonElement s : jsonarray) {
                        fields.add(s.getAsString());
                    }
                    for (String s : fields) {
                        String[] _keyTree = s.split("\\.");
                        String jsonModResult = _murlinObj.getResults().get(_keyTree[0].trim());
                        if (jsonModResult != null) {
                            String val = getValue(_keyTree, jsonModResult);
                            if (val != null) {
                                factsTerm.put(s, val);
                            }
                        }
                    }
                    DataTypeChecker dtypechckr;
                    DataTypeChecker dtypechckrinr;

                    for (Map.Entry<String, String> entry : factsTerm.entrySet()) {
                        String key_term = entry.getKey();
                        String value = validateNullValue(entry.getValue().trim().replaceAll("\\s+", ""));
                        dtypechckr = new DataTypeChecker(value);
                        if (dtypechckr.isArray()) {
                            String[] arrayValues = value.replaceAll("\"|\\[|\\]", "").split(",");
                            for (String val : arrayValues) {
                                dtypechckrinr = new DataTypeChecker(val);
                                if (dtypechckrinr.isInteger() || dtypechckr.isBoolean()) {
                                    optionalFacts.add(constructFacts(key_term, val));
                                }
                                if (dtypechckrinr.isString()) {
                                    optionalFacts.add(constructFacts(key_term, val));
                                }
                            }
                        } else if (dtypechckr.isString()) {
                            optionalFacts.add(constructFacts(key_term, value));
                        } else if (dtypechckr.isInteger() || dtypechckr.isBoolean()) {
                            optionalFacts.add(constructFacts(key_term, value));
                        }
                    }
                    _logger.debug("Generated Optional Facts :: " + optionalFacts);
                }
            }
        } catch (Exception ex) {
            throw new CiaException(ex);
        }
        return optionalFacts;
    }

    /**
     * Uses wrs.common DefaultWRSConstants positive, negative and malicious
     * mappings for checking.
     *
     * @parameter category as List of Strings
     *
     * @return null = invalid / unrated / normal / negative / malicious
     */
    private String getScoreType(List<String> catList) {
        boolean untested = false;
        boolean normal = false;
        boolean negative = false;
        boolean malicious = false;
        boolean new_url = false;
        if (catList != null) {
            for (String cat : catList) {
                if (cat.equalsIgnoreCase(String.valueOf(DefaultWRSConstants.DEFAULT_UNKNOWN_RATING))) {
                    untested = true;
                } else if (cat.equalsIgnoreCase(String.valueOf(DefaultWRSConstants.DEFAULT_NEW_DOMAIN))) {
                    new_url = true;
                } else if (DefaultWRSConstants.DEFAULT_POSITIVE_RATING_MAP.get(cat.trim()) != null) {
                    normal = true;
                } else if (DefaultWRSConstants.DEFAULT_NEGATIVE_RATING_MAP.get(cat.trim()) != null) {
                    negative = true;
                } else if (DefaultWRSConstants.DEFAULT_MALICIOUS_RATING_MAP.get(cat.trim()) != null) {
                    malicious = true;
                }
            }
        }
        if (malicious) {
            return "malicious";
        }
        if (negative) {
            return "negative";
        }
        if (normal) {
            return "normal";
        }
        if (untested) {
            return "untested";
        }
        if (new_url) {
            return "new_url";
        }        
        return null;
    }
}
