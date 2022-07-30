import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.cbor.databind.CBORMapper;
import lombok.extern.slf4j.Slf4j;
import org.graalvm.nativeimage.IsolateThread;
import org.graalvm.nativeimage.UnmanagedMemory;
import org.graalvm.nativeimage.c.function.CEntryPoint;
import org.graalvm.nativeimage.c.type.CCharPointer;
import org.graalvm.nativeimage.c.type.CCharPointerPointer;
import org.graalvm.nativeimage.c.type.CIntPointer;
import org.graalvm.nativeimage.c.type.CTypeConversion;

@Slf4j
public class Shared {

    static Map<String, Function<String, Object>> methods;
    static ObjectMapper cborMapper = new CBORMapper();
    static ObjectMapper jsonMapper = new JsonMapper();


    @CEntryPoint(name = "JGateway__init", documentation = "Initializes the gateway")
    static void init(IsolateThread thread) {
        log.info("Initializing method map. Encoding={}", System.getProperty("file.encoding"));
        methods = new HashMap<>();
        methods.put("startGame", GameInterface::startGame);
        methods.put("flood", s -> GameInterface.flood(s));
    }


    @CEntryPoint(name = "JGateway__invoke", documentation = "calls a registered method with a string parameter")
    static int invoke(IsolateThread thread, CCharPointer methodNamePtr, CCharPointer messagePtr,
                      CCharPointerPointer out, CIntPointer outSize) {
        final String methodName = CTypeConversion.toJavaString(methodNamePtr);
        final String message = CTypeConversion.toJavaString(messagePtr);
        if (methods.containsKey(methodName)) {
            try {
                final byte[] bytes = cborMapper.writeValueAsBytes(methods.get(methodName).apply(message));
                final CCharPointer heapMem = UnmanagedMemory.malloc(bytes.length);
                for(int i = 0; i< bytes.length; i++){
                    heapMem.write(i, bytes[i]);
                }
                out.write(heapMem);
                outSize.write(bytes.length);
                log.info(cborMapper.reader().readTree(bytes).fieldNames().next());
            } catch (IOException e) {
                log.error("error creating json", e);
            }
        } else {
            outSize.write(0);
            log.warn("was invoked with " + methodName + ", but this method is not known");
        }
        return 0;
    }
}
