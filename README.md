
### 项目结构
request->executor->execute()


param->action->doBusiness()


ProcessorManager->Processor->process()


HandlerContainer -> handler ->invoke()


ServiceUtil->command -> execute()


### sentinel

1.7.1版本，从控制台到适配器都要版本一致

启动参数
-Djava.net.preferIPv4Stack=true
-Dcsp.sentinel.api.port=8719
-Dcsp.sentinel.dashboard.server=localhost:8080
-Dproject.name=dubboprovider


#### demo
consumer端熔断：initdegradeRule ，每分钟异常数超过5开始熔断
provider端限流：initFlowRule，qps=5




