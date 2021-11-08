package oit.is.z0209.kaizi.janken2.model;

public class Janken {
  String cpuHand;
  String hand;
  String kekka;

  public Janken(String hand) {
    this.hand = hand;
    this.cpuHand = "Gu";

    if (this.hand.equals("Gu")) {
      this.kekka = "Draw!";
    }
    if (this.hand.equals("Choki")) {
      this.kekka = "You lose!";
    }
    if (this.hand.equals("Pa")) {
      this.kekka = "You Win!";
    }
  }

  public String getKekka() {
    return kekka;
  }

  public void setKekka(String kekka) {
    this.kekka = kekka;
  }

  public String getCpuHand() {
    return cpuHand;
  }

  public void setCpuHand(String cpuHand) {
    this.cpuHand = cpuHand;
  }

  public String getHand() {
    return hand;
  }

  public void setHand(String hand) {
    this.hand = hand;
  }

}
