package my.powerassert;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

// TODO detect expressions in test methods without assert statement??

//@SupportedAnnotationTypes("my.powerassert.Assert")
@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class AssertProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        EnvironmentProvider environmentProvider = createEnvironmentProvider();
        ProcessorIntf processor = environmentProvider.createProcessorIntf();
        processor.process(environmentProvider, processingEnv, roundEnv);
        return true;
    }

    private static EnvironmentProvider createEnvironmentProvider() {
        // TODO try non-reflective creation
        String clazz;
        switch (CompilerVersion.current()) {
            case JDK6: clazz = "my.powerassert.EnvironmentProvider6"; break;
            case JDK7: clazz = "my.powerassert.EnvironmentProvider7"; break;
            case JDK8: clazz = "my.powerassert.EnvironmentProvider8"; break;
            case ECJ: clazz = "my.powerassert.ecj.EnvironmentProviderECJ"; break;
            default: throw new IllegalStateException(CompilerVersion.current().name());
        }
        try {
            return (EnvironmentProvider) Class.forName(clazz).newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Cannot create EnvironmentProvider: " + e.getMessage(), e);
        }
    }
}
