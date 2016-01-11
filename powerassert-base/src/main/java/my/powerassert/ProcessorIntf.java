package my.powerassert;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;

public interface ProcessorIntf {
    void process(EnvironmentProvider environmentProvider, ProcessingEnvironment processingEnv, RoundEnvironment roundEnv);
}
