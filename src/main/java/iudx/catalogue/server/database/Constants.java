package iudx.catalogue.server.database;

public class Constants {

  /* General purpose */
  static final String SEARCH = "search";
  static final String COUNT = "count";
  static final String DESCRIPTION = "detail";
  static final String HTTP = "http";
  static final String ATTRIBUTE = "attrs";
  static final String RESULT = "results";
  static final String SHAPE_KEY = "shape";
  static final String SIZE_KEY = "size";
  static final int STATIC_DELAY_TIME = 3000;

  /* Database */
  static final String AGGREGATION_KEY = "aggs";
  
  static final String FILTER_PATH = "?filter_path=took,hits.total.value,hits.hits._source";
  static final String FILTER_PATH_AGGREGATION = "?filter_path=hits.total.value,aggregations.results.buckets";
  static final String FILTER_ID_ONLY_PATH = "?filter_path=hits.total.value,hits.hits._id&size=10000";
  
  static final String TYPE_KEY = "type";
  static final String ID_KEYWORD = "id.keyword";
  static final String DOC_ID = "_id";
  static final String KEY = "key";
  static final String SUMMARY_KEY = "_summary";
  static final String GEOSUMMARY_KEY = "_geosummary";

  /* Geo-Spatial */
  static final String COORDINATES_KEY = "coordinates";
  static final String DISTANCE_IN_METERS = "m";
  static final String GEO_BBOX = "envelope";
  static final String GEO_CIRCLE = "circle";
  static final String GEO_KEY = ".geometry";
  static final String GEO_RADIUS = "radius";
  static final String GEO_RELATION_KEY = "relation";
  static final String GEO_SHAPE_KEY = "geo_shape";

  /* Error */
  static final String DATABASE_BAD_QUERY = "Query Failed with status != 20x";
  static final String EMPTY_RESPONSE = "Empty response";

  static final String NO_SEARCH_TYPE_FOUND = "No searchType found";
  static final String COUNT_UNSUPPORTED = "Count is not supported with filtering";
  static final String INVALID_SEARCH = "Invalid search request";

  static final String ERROR_DB_REQUEST = "DB request has failed";
  public static final String ERROR_INVALID_PARAMETER = "Incorrect/missing query parameters";
  static final String DOC_EXISTS = "item already exists";
  static final String INSTANCE_NOT_EXISTS = "instance doesn't exist";

  /** Search type regex */
  public static final String TAGSEARCH_REGEX = "(.*)tagsSearch(.*)";
  public static final String TEXTSEARCH_REGEX = "(.*)textSearch(.*)";
  public static final String ATTRIBUTE_SEARCH_REGEX = "(.*)attributeSearch(.*)";
  public static final String GEOSEARCH_REGEX = "(.*)geoSearch(.*)";
  public static final String RESPONSE_FILTER_GEO = "responseFilter_geoSearch_";
  public static final String RESPONSE_FILTER_REGEX = "(.*)responseFilter(.*)";

  /** DB Query related */
  public static final String MATCH_KEY = "match";
  public static final String TERMS_KEY = "terms";
  public static final String STRING_QUERY_KEY = "query_string";
  public static final String FROM = "from";
  public static final String KEYWORD_KEY = ".keyword";
  public static final String DEVICEID_KEY = "deviceId";
  public static final String TAG_AQM = "aqm";
  public static final String DESCRIPTION_ATTR = "description";

  /** ElasticClient search types */
  public static final String DOC_IDS_ONLY = "DOCIDS";
  public static final String SOURCE_ONLY = "SOURCE";

  public static final String FORWARD_SLASH = "/";
  public static final String WILDCARD_KEY = "wildcard";
  public static final String AGGREGATION_ONLY = "AGGREGATION";
  public static final String TYPE_KEYWORD = "type.keyword";
  public static final String WORD_VECTOR_KEY = "_word_vector";

  /** Some queries */
  public static final String LIST_INSTANCES_QUERY = "{\"size\": 0, \"aggs\":"
    + "{\"results\": {\"terms\":"
    + "{\"field\":instances.keyword,"
    + "\"size\": 10000}}}}";

  public static final String LIST_INSTANCE_TAGS_QUERY = 
    "{\"query\": {\"bool\": {\"filter\": {\"term\": {\"instance.keyword\": \"$1\"}}}},"
    + "\"aggs\":"
    + "{\"results\": {\"terms\":"
    + "{\"field\":\"tags.keyword\","
    + "\"size\": $size}}}}";

  public static final String  LIST_TAGS_QUERY = 
    "{ \"aggs\":"
    + "{\"results\": {\"terms\":"
    + "{\"field\":\"tags.keyword\","
    + "\"size\": $size}}}}";

  public static final String LIST_INSTANCE_TYPES_QUERY = 
    "{\"query\": {\"bool\": {\"filter\": [ {\"match\": {\"type\": \"$1\"}},"
                          + "{\"term\": {\"instance.keyword\": \"$2\"}}]}},"
    + "\"aggs\": {\"results\": {\"terms\": {\"field\": \"id.keyword\", \"size\": $size}}}}";

  public static final String LIST_TYPES_QUERY = 
    "{\"query\": {\"bool\": {\"filter\": [ {\"match\": {\"type\": \"$1\"}} ]}},"
    + "\"aggs\": {\"results\": {\"terms\": {\"field\": \"id.keyword\", \"size\": $size}}}}";

  public static final String GEO_SHAPE_QUERY =
      "{ \"geo_shape\": { \"$4\": { \"shape\": { \"type\": \"$1\", \"coordinates\": $2 },"
          + " \"relation\": \"$3\" } } }";

  public static final String TEXT_QUERY =
      "{\"query_string\":{\"query\":\"$1\"}}";

  public static final String GET_DOC_QUERY =
      "{\"_source\":[$2],\"query\":{\"term\":{\"id.keyword\":\"$1\"}}}";

  public static final String INSTANCE_FILTER = "{\"match\":" + "{\"instance\": \"" + "$1" + "\"}}";
  public static final String BOOL_MUST_QUERY = "{\"query\":{\"bool\":{\"must\":[$1]}}}";
  public static final String SHOULD_QUERY = "{\"bool\":{\"should\":$1}}";
  public static final String MUST_QUERY = "{\"bool\":{\"must\":$1}}";
  public static final String FILTER_QUERY = "{\"bool\":{\"filter\":[$1]}}";
  public static final String MATCH_QUERY = "{\"match\":{\"$1\":\"$2\"}}";
  public static final String TERM_QUERY = "{\"term\":{\"$1\":\"$2\"}}";

  public static final String QUERY_RESOURCE_GRP =
      "{ \"query\": { \"bool\": { \"should\": [ { \"term\": { \"id.keyword\": \"$1\" } }, "
          + "{ \"term\": { \"resourceGroup.keyword\": \"$2\" } } ] } } }";

  public static final String NLP_SEARCH = "{\"query\": {\"script_score\": {\"query\": {\"match_all\": {}},\"script\":"
  + "{\"source\": \"cosineSimilarity(params.query_vector, '_word_vector'') + 1.0\",\"lang\":\"painless\",\"params\": "
  +"{\"query_vector\": \"$1\"}}}}}";
  
  public static final String NLP_LOCATION_SEARCH = "{\"query\": {\"script_score\": {\"query\": {\"bool\": {\"must\":"
   +"{\"match_all\": {}},\"filter\": {\"geo_shape\": {\"location.geometry\": {\"shape\": {\"type\": \"envelope\",\"coordinates\":"
   + "[ [\"$1\" , \"$2\"], [ \"$3\" , \"$4\"]]},\"relation\": \"within\"}}}}},\"script\": {\"source\":"
   + "\"cosineSimilarity(params.query_vector, '_word_vector'') + 1.0\",\"params\": {\"query_vector\": $5 }}}}}";
}
