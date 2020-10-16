# broadcaster
一个消息处理的中间类

分为2个模块  
1.starter  
2.core  

##供springboot项目使用


###如何使用
####配置文件中配置  
broadcaster.id=firstId,secondId

#####  注释  firstId MQ对应的配置
broadcaster.firstId.provider=rabbitmq  
broadcaster.firstId.host=host  
broadcaster.firstId.port=port  
broadcaster.firstId.username=guest  
broadcaster.firstId.password=guest  
broadcaster.firstId.routingKey=routingKey  
broadcaster.firstId.channel=com.channel  

##### secondId MQ对应的配置
broadcaster.secondId.provider=rabbitmq  
broadcaster.secondId.host=host  
broadcaster.secondId.port=port  
broadcaster.secondId.username=guest  
broadcaster.secondId.password=guest  
broadcaster.secondId.routingKey=routingKey  
broadcaster.secondId.channel=com.channel  

####代码中注入
    @Qualifier("firstIdBroadcaster")
    @Autowired
    private Broadcaster firstIdBroadcaster;
    
    @Qualifier("secondIdBroadcaster")
    @Autowired
    private Broadcaster secondIdBroadcaster;


