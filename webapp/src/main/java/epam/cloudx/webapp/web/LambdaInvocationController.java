package epam.cloudx.webapp.web;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.model.InvokeRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lambda")
public class LambdaInvocationController {
    private final AWSLambda awsLambda;

    public LambdaInvocationController(AWSLambda awsLambda) {
        this.awsLambda = awsLambda;
    }

    @GetMapping("invoke/{arn}")
    public void invokeLambda(@PathVariable String arn) {
        InvokeRequest invokeRequest = new InvokeRequest()
                .withFunctionName(arn)
                .withPayload("{\"detail-type\" : \"Application\"}");

        awsLambda.invoke(invokeRequest);
    }
}
