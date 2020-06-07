视频讲解流程

ES 基本操作

    HouseIndexKey   索引关键词统一定义
    ElasticSearchConfig

    ISearchService -- CRUD  create()  update()  deleteAndCreate()   index()
                      createOrUpdateIndex()  新增或者修改

    SearchServiceTests

    HouseServiceImpl   updateStatus()--跟新数据库状态，并且更新ES


Kafka 异步处理消息

    当 添加房源信息 同步 ES 创建 index ，如果 创建index慢，会影响用户体验，
    那么可以使用Kafka 异步处理消息

    SearchServiceImpl  handleMessage()  监听消息

    SearchServiceImpl index()  发送消息


查询接口
    SearchServiceImpl  query()

分词处理
    house_index_with_ik_mapping.json
    house_index_with_ik_max_word_mapping.json

Search-as-you-type
    搜索补全  用Suggesters
    HouseController  autocomplete()
    house_index_with_suggest.json

    在es数据的更新或者修改时，对Suggesters进行填充 updateSuggest()