package se233.chapter1.model.character;

import se233.chapter1.model.DamageType;
import se233.chapter1.model.item.Armor;
import se233.chapter1.model.item.Weapon;

import java.util.Set;

public class BasedCharacter {
    protected String name, imgpath;
//    protected DamageType type;
    protected Set<DamageType> types;
    protected Integer fullHp, basedPow, basedDef, basedRes;
    protected Integer hp, power, defense, resistance;
    protected Weapon weapon;
    protected Armor armor;
    public String getName (){return name;}
    public String getImagepath (){return imgpath;}
    public Integer getHp (){return hp ;}
    public Integer getFullHp (){return fullHp ;}
    public Integer getPower (){return power ;}
    public Integer getDefense (){return defense ;}
    public Integer getResistance (){return resistance ;}

    public void equipWeapon( Weapon weapon) {
        if (!types.contains(weapon.getDamageType())) {
            System.out.println("Cannot equip weapon: DamageType mismatch.");
            return;
        }
        this.weapon = weapon;
        this.power = this.basedPow + weapon.getPower();
    }

    public void equipArmor( Armor armor) {
        this.armor = armor;
        this.defense = this.basedDef + armor.getDefense();
        this.resistance= this.basedRes + armor.getResistance();
    }

    public void unequipWeapon() {
        this.weapon = null;
        this.power = this.basedPow;
    }

    public void unequipArmor() {
        this.armor = null;
        this.defense = this.basedDef;
        this.resistance = this.basedRes;
    }

    @Override
    public String toString (){return name ;}
    public Set<DamageType> getTypes() {
        return types;
    }

//    public DamageType getType(){
//        return type;
//    }

}

