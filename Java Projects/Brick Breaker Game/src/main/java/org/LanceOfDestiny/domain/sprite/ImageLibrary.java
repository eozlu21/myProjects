package org.LanceOfDestiny.domain.sprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public enum ImageLibrary {

    FireBall("Image/Fireball.png"),
    FireBallOverwhelmed("Image/FireballOverwhelmed.png"),
    RewardingBarrier("Image/RewardingBarrier.png"),
    Heart("Image/Heart.png"),
    MagicalStaff("Image/MagicalStaff.png"),
    RewardBox("Image/RewardBox.png"),
    CannonSpell("Image/CannonSpell.png"),

    OverWhelmingSpell("Image/OverwhelmSpell.png"),
    ExpansionSpell("Image/ExpansionSpell.png"),
    ExplosiveBarrier("Image/ExplosiveBarrier.png"),
    SimpleBarrier("Image/SimpleBarrier.png"),
    ReinforcedBarrier("Image/ReinforcedBarrier.png"),

    DoubleAccelSpell("Image/hourglass.png"),
    HollowSpell("Image/inf_void.png"),
    InfVoidSpell("Image/snow_flake.png"),

    Canon("Image/Canon.png"),
    Background("Image/Background.png"),

    RegularSpeaker("Image/speaker.png"),
    MutedSpeaker("Image/mutedSpeaker.png"),

    Ymir("Image/Ymir.png"),
    HollowBarrier("Image/HollowBarrier.png"),
    FrozenBarrierCircle("Image/FrozenExplosive.png"),
    FrozenBarrierRectangle("Image/FrozenBarrier.png"),

    HelpScreen("Image/HelpScreen.png");



    private final BufferedImage image;

    ImageLibrary(String path){

        try {
            this.image = ImageIO.read(new File(path));
            if(this.image == null)throw new RuntimeException("Image not found but loaded");
        } catch (IOException e) {
            throw new RuntimeException(String.format("Path : %s doesnt exist", path));
        }
    }

    public BufferedImage getImage(){
        return image;
    }
}
