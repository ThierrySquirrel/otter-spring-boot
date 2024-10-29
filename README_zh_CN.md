# otter-spring-boot-starter

水獭   SpringBoot 版  

[English](./README.md)  

支持功能：  
- [x] 确保调用一致性  

# 确保调用一致性：  
  使用之前,请确保调用链路,为幂等性.   
  简单讲解:服务A调用服务B,服务B调用服务C,链路中,B与C执行成功,  
  A执行异常,这时则出现数据不一致.    
  水獭则会尝试重新调用链路,  
  服务A调用服务B,服务B调用服务C.    
  链路调用成功,则数据保持一致性.    
  
## Quick Start  

```xml
<!--在pom.xml中添加依赖-->
        <dependency>
            <artifactId>otter-spring-boot-starter</artifactId>
            <groupId>com.github.thierrysquirrel</groupId>
            <version>2.1.1.4-RELEASE</version>
        </dependency>
```  

# 开始使用  

```java
@RestController
@Otter
public class Demo {
	
    @Resource
	public FeignService feignService;

	@Reference
	private DubboService dubboService;

    @Resource
    private ButterService butterService;

    @GetMapping("/local")
    public String local(){
        //本地SQL执行
        return "local";
    }

	@Repair
	@GetMapping("/feign")
	public String feign() {
        feignService.update();
        //本地SQL执行
		return "feign";
	}

    @Repair
	@GetMapping("/dubbo")
	public String dubbo() {
        dubboService.update();
        //本地SQL执行
		return "dubbo";
	}

    @Repair
	@GetMapping("/butter")
	public String butter() {
        butterService.update();
        //本地SQL执行
		return "butter";
	}
}
```

# 添加Feign支持

```java
@Component
public class OtterFeignRequestInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {
		Optional<Long> globalId = Optional.ofNullable(GlobalIdUtils.getId());
		globalId.ifPresent(id -> template.header(InterceptorConstant.INTERCEPTOR_IDENTIFIER, String.valueOf(id)));
	}
}

@Component
public class OtterOncePerRequestFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		Optional<String> globalId = Optional.ofNullable(request.getHeader(InterceptorConstant.INTERCEPTOR_IDENTIFIER));
		globalId.ifPresent(id -> GlobalIdUtils.setId(Long.valueOf(id)));
		filterChain.doFilter(request, response);
	}
}
```

# 添加dubbo-spring-boot-starter支持

```java
@Activate(group = CommonConstants.CONSUMER)
public class OtterDubboConsumerFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) {
        Optional<Long> globalId = Optional.ofNullable(GlobalIdUtils.getId());
        globalId.ifPresent(id -> RpcContext.getContext().setAttachment(InterceptorConstant.INTERCEPTOR_IDENTIFIER, String.valueOf(id)));
        return invoker.invoke(invocation);
    }
}

@Activate(group = CommonConstants.PROVIDER)
public class OtterDubboProviderFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) {
        Optional<Object> globalId = Optional.ofNullable(RpcContext.getContext().getAttachment(InterceptorConstant.INTERCEPTOR_IDENTIFIER));
        globalId.ifPresent(id -> GlobalIdUtils.setId((Long.valueOf(String.valueOf(id)))));
        return invoker.invoke(invocation);
    }
}
```

# 添加butter支持

```java
@ButterflyFilter
public class ButterConsumerFilter implements Filter {
	@Override
	public void filter(PineRequestContextFilterDomain pineRequestContextFilterDomain) {
		Optional<Long> globalId = Optional.ofNullable(GlobalIdUtils.getId());
		globalId.ifPresent(id -> pineRequestContextFilterDomain.setAttachment(InterceptorConstant.INTERCEPTOR_IDENTIFIER, String.valueOf(id)));
	}
}

@FlowerFilter
public class ButterProducerFilter implements Filter {
	@Override
	public void filter(PineRequestContextFilterDomain pineRequestContextFilterDomain) {
		Optional<String> globalId = Optional.ofNullable(pineRequestContextFilterDomain.getAttachment(InterceptorConstant.INTERCEPTOR_IDENTIFIER));
		globalId.ifPresent(id -> GlobalIdUtils.setId((Long.valueOf(id))));
	}
}
```


