package model.entity.system;


import lombok.Data;
import model.entity.base.BaseEntity;

@Data
public class SysRoleUser extends BaseEntity {

    private Long roleId;       // 角色id
    private Long userId;       // 用户id

}
