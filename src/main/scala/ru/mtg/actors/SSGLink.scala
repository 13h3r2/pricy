package ru.mtg.actors

object SSGLink {
  def apply(offset : Int = 0, numpage : Int = 100) = {
    "http://sales.starcitygames.com//spoiler/display.php?" +
      "&s_all=All" +
      "&r_all=All" +
      "&t_all=All" +
      "&g_all=All" +
      "&sort1=4&sort2=1&sort3=10" +
      "&display=3" +
      "&startnum=" + offset +
      "&numpage=" + numpage
  }
}
