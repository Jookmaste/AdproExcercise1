package se233.chapter1.model.character;

import se233.chapter1.model.DamageType;

import java.util.Set;

public class BattleMageCharacter extends BasedCharacter {
    public BattleMageCharacter(String name, String imgpath, int basedDef, int basedRes){
        this.name = name;
        this.types = Set.of(DamageType.physical, DamageType.magical);
        this.imgpath = imgpath;
        this.fullHp = 40;
        this.basedPow = 40;
        this.basedDef = basedDef;
        this.basedRes = basedRes;
        this.hp = this.fullHp;
        this.power = this.basedPow;
        this.defense = basedDef;
        this.resistance = basedRes;
    }
}