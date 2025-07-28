# otter-spring-boot-starter  

Otter Spring Book Edition  

[中文](./README_zh_CN.md)  

Support Function：  
- [x] Ensure Call Consistency  

# Ensure Call Consistency：  

  Before Using, Make Sure That The Call Link Is Idempotent  
  Simple Explanation: Service A Calls Service B, Service B Calls Service C,   
  in the link, B and C execute successfully     
  A Execute Exception, Then The Data Is Inconsistent   
  The Otter Will Try To Call The Link Again  
  Service A Calls Service B, And Service B Calls Service C  
  If The Link Call Is Successful, The Data Is Consistent  
  
## Quick Start  

```xml
<!--Adding dependencies to pom. XML-->
        <dependency>
            <artifactId>otter-spring-boot-starter</artifactId>
            <groupId>io.github.thierrysquirrel</groupId>
            <version>2.2.0.0-RELEASE</version>
        </dependency>
```  

# Start Using  

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
        //Local SQL Execution
        return "local";
    }

	@Repair
	@GetMapping("/feign")
	public String feign() {
        feignService.update();
        //Local SQL Execution
		return "feign";
	}

    @Repair
	@GetMapping("/dubbo")
	public String dubbo() {
        dubboService.update();
        //Local SQL Execution
		return "dubbo";
	}

    @Repair
	@GetMapping("/butter")
	public String butter() {
        butterService.update();
        //Local SQL Execution
		return "butter";
	}

}
```

# Add Feign Support

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

# Add dubbo-spring-boot-starter Support

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

# Add butter Support

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

