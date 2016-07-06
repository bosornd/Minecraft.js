# Minecraft.js

Minecraft.js는 Javascript로 Minecraft를 쉽게 확장할 수 있는 Forge 1.7.10 모드다.

# 다운로드 & 빌드

1. `Clone or download`에서 소스 코드를 다운로드 받는다. zip파일로 받아서 설치해도 되고, Clone하면 더 좋고.
2. 명령어 창에서 `gradlew setupDecompWorkspace`를 실행하면, 빌드에 필요한 라이브러리를 인터넷에서 다운로드 받는다. 당연히 인터넷에 연결되어 있어야 함.
3. 명령어 창에서 `gradlew build`를 실행하면, `build/libs` 폴더에 모드 파일이 생성된다.
4. 생성된 모드 파일(minecraftjs-forge.1.7.10-SNAPSHOT-<버전>.jar)을 Forge 모드 폴더에 복사해서 사용하면 된다.

# 사용법

Minecraft에서 `js`, `jsc` 명령어를 지원한다.

## `js` 명령어

* `/js <javascript file(확장자 .js)> <arg1> <arg2> <arg3> ...`
* `/js <javascript program>`

## `jsc` 명령어

* `/jsc reload` : Javascript 동작 환경을 초기화 한다.

# Nashorn vs. Rhino

[Nashorn](http://openjdk.java.net/projects/nashorn/)은 [Rhino](https://developer.mozilla.org/ko/docs/Rhino)를 대신하여 Java SE 8에 포함된 Javascript 엔진이다. Nashorn은 Javascript의 숫자를 가능한 정수형으로 처리함으로써 성능향상을 도모하고 있는데, 이로 인하여 다음과 같은 문제가 있다.

```
var Point = Java.type('java.awt.Point');
var p = new Point(10, 20);
for(var x=0; x<10; x++){
	p.setLocation(x,0);
	print(p);
}
```

상기 Javascript 프로그램을 `jjs`로 실행하면 다음과 같은 오류를 발생한다.

```
java.awt.Point[x=0,y=0]
Exception in thread "main" java.lang.RuntimeException: java.lang.NoSuchMethodException: Can't unambiguously select between fixed arity signatures [(int, int), (double, double)] of the method java.awt.Point.setLocation for argument types [java.lang.Double, java.lang.Integer]
	at jdk.nashorn.internal.runtime.ScriptRuntime.apply(ScriptRuntime.java:397)
	at jdk.nashorn.tools.Shell.apply(Shell.java:393)
	at jdk.nashorn.tools.Shell.runScripts(Shell.java:322)
	at jdk.nashorn.tools.Shell.run(Shell.java:171)
	at jdk.nashorn.tools.Shell.main(Shell.java:135)
	at jdk.nashorn.tools.Shell.main(Shell.java:111)
Caused by: java.lang.NoSuchMethodException: Can't unambiguously select between fixed arity signatures [(int, int), (double, double)] of the method java.awt.Point.setLocation for argument types [java.lang.Double, java.lang.Integer]
	at jdk.internal.dynalink.beans.OverloadedMethod.throwAmbiguousMethod(OverloadedMethod.java:225)
	at jdk.nashorn.internal.scripts.Script$test.:program(test.js:4)
	at jdk.nashorn.internal.runtime.ScriptFunctionData.invoke(ScriptFunctionData.java:623)
	at jdk.nashorn.internal.runtime.ScriptFunction.invoke(ScriptFunction.java:494)
	at jdk.nashorn.internal.runtime.ScriptRuntime.apply(ScriptRuntime.java:393)
	... 5 more
```

for loop에서 첫번째 `x=0`인 경우에는, `p.setLocation(<int>, <int>)`으로 정상적으로 출력되었지만, `x++`가 실행된 이후에는, `x`가 double로 바뀌어서 `p.setLocation(<double>,<int>)`가 호출되면서 exception이 발생한다.

이런 문제로 인하여, Minecraft.js에서는 Rhino를 사용한다. 나중에는 V8을 사용하는 것을 검토해 볼 생각이다. [J2V8](https://github.com/eclipsesource/j2v8)는 Java에서 V8을 binding하는 방법을 제공하고 있다.

# CommonJS in Rhino

[Rhino 1.7R3](https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino/New_in_Rhino_1.7R3)부터 [CommonJS module](http://wiki.commonjs.org/wiki/Modules/1.1.1)을 지원한다. Node.js처럼 `require` 함수로 외부 모듈을 import하여 사용할 수 있다.

다음은 Rhino를 사용하는 간단한 예이다.

```
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.tools.shell.Global;

public class RhinoTest {
	
    public static void main(String[] args) {
        // Prints "Hello, World" to the terminal window.
        System.out.println("Hello, World");
        
        Context cx = Context.enter();
        try {
            Global global = new Global();
            global.initStandardObjects(cx, true);
//            cx.setLanguageVersion(170); // enable 1.7 language features
            List<String> modulePaths = Arrays.asList("./modules");
            global.installRequire(cx, modulePaths, false);
            
            String file = "test.js";
            Object result = cx.evaluateReader(global, new InputStreamReader(new FileInputStream(new File(file))), file, 1, null);
            if(!(result instanceof org.mozilla.javascript.Undefined))
            	System.out.println(result.toString());
        }
        catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
        }
    	finally {
            // Exit from the context.
            Context.exit();
        }
   }
}
```

test.js

```
var Math = require('math');
Math.add(10, 20);
```

modules/math.js

```
exports.add = function(a, b) {
	return a + b;
};
```

