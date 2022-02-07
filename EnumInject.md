An example of an @EnumInject mixin that adds a stone boat
```java
@Mixin(BoatEntity.Type.class)
public abstract class BoatEntityTypeMixin {
    @Invoker("<init>")
    public static BoatEntity.Type callInit(String name, int ordinal, Block baseBlock, String name0) {
        throw new Error();
    }

    @EnumInject(name = "STONE",
            method = "<clinit>",
            at = @At(value = "INVOKE",
                    target = "net/minecraft/entity/vehicle/BoatEntity$Type.method_36671()[Lnet/minecraft/entity/vehicle/BoatEntity$Type;"))
    private static BoatEntity.Type createOrange(int ordinal) {
        return callInit("STONE", 4, Blocks.STONE, "stone");
    }
}```
