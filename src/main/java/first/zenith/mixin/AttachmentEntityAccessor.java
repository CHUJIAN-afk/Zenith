package first.zenith.mixin;

import first.lyra.common.attachmentEntity.AttachmentEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AttachmentEntity.class)
public interface AttachmentEntityAccessor {
    @Accessor
    void setRemove(boolean remove);
}
