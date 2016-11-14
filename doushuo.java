/**
 * Created by Administrator on 2016/10/15.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
//实现斗兽棋的基本玩法。
public class doushuo {
    public static void main(String[] args) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("tile.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner input = null;
        try {
            input = new Scanner(new File("animal.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }/*导入地图文本*/
        int i;
        String[][] table = new String[7][9];
        char[] number1 = new char[]{'0', '1', '2', '3', '4', '5'};
        String[] map = new String[]{" 　 ", " 水 ", " 陷 ", " 家 ", " 陷 ", " 家 "};
        char[] number2 = new char[]{'1', '2', '3', '4', '5', '6', '7', '8'};
        String[] animals = new String[]{"鼠", "猫", "狼", "狗", "豹", "虎", "狮", "象"};
        String[][] animals1 = new String[][]{{"1鼠 ", " 鼠1"}, {"2猫 ", " 猫2"}, {"3狼 ", " 狼3"}, {"4狗 ", " 狗4"}, {"5豹 ", " 豹5"}, {"6虎 ", " 虎6"}, {"7狮 ", " 狮7"}, {"8象 ", " 象8"}, {"haha", "haha"}};
        String[][][] boardHistory = new String[100][][];
        int currentStep = 0, lastStep = 0, nextStep = 0;
        for (i = 0; i < 7; i++) {
            String animalline = input.nextLine();
            String dituline = scanner.nextLine();
            int k;
            for (k = 0; k < 9; k++) {
                char animal = animalline.charAt(k);
                if (animal == '0') {
                    for (int b = 0; b < 6; b++) {
                        if (dituline.charAt(k) == number1[b]) {
                            table[i][k] = map[b];
                            System.out.print(map[b]);
                        }
                    }
                } else {
                    for (int a = 0; a < 8; a++) {
                        int c = a + 1;
                        if (animalline.charAt(k) == number2[a] && k <= 4) {
                            table[i][k] = animals1[a][0];
                            System.out.print(animals1[a][0]);
                        }
                        if (animalline.charAt(k) == number2[a] && k > 4) {
                            table[i][k] = animals1[a][1];
                            System.out.print(animals1[a][1]);
                        }
                    }

                }
            }
            System.out.println();
        }/*输出地图并导入二维数组中*/
        boardHistory[0] = copyBoard(table);
        int g = 0;
        int r=0;
        Scanner input1 = new Scanner(System.in);
        for (int h = 0; h < 100; h++) {
            if (g % 2 == 0) {
                System.out.print("左方玩家行动：");
            } else {
                System.out.print("右方玩家行动：");
            }
            String order1 = input1.next();
            switch (order1) {
                case "help":
                    System.out.println("指令介绍：");
                    System.out.println("");
                    System.out.println("1.移动指令：");
                    System.out.println("移动指令由两个部分组成。");
                    System.out.println("第一个部分是数字1-8，根据战斗力分别对应鼠（1），猫（2），狼（3），狗（4），豹（5），虎（6），狮（7），象（8)");
                    System.out.println("第二个部分是字母wasd中的一个，w对应上方向，a对应左方向，s对应下方向，d对应右方向");
                    System.out.println("比如指令“1d”表示鼠向右走，“4w”表示狗向上走");
                    System.out.println("");
                    System.out.println("2.游戏指令：");
                    System.out.println("输入restart重新开始游戏");
                    System.out.println("输入help查看帮助");
                    System.out.println("输入undo悔棋");
                    System.out.println("输入redo取消悔棋");
                    System.out.println("输入exit退出游戏");
                    g--;
                    break;
                case "exit":
                    System.out.println("游戏结束！");
                    h = 100;
                    r=1;
                    break;
                case "undo":
                    nextStep = currentStep - 1;
                    if (nextStep >= 0) {
                        currentStep = nextStep;
                        g++;
                    } else {
                        System.out.println("已经回到最初状态，不能再悔棋了");
                    }
                    break;
                case "redo":
                    nextStep = currentStep + 1;
                    if (nextStep <= lastStep) {
                        currentStep = nextStep;
                        g++;
                    } else {
                        System.out.println("不能再取消悔棋了！");
                    }
                    break;
                case "restar":
                    currentStep = 0;
                    g = 0;
                    break;
                default:
                    int v = order1.charAt(0) - '0';
                    if (order1.length() != 2) {
                        System.out.println("请输入合法指令");
                        break;
                    } else if (v < 1 || v > 8) {
                        System.out.println("请输入合法指令");
                        break;
                    } else {
                        int l = 0;
                        int p = 0;
                        int q = 0;
                        for (int w = 0; w < 7; w++) {
                            for (int y = 0; y < 9; y++) {
                                if (table[w][y].equals(animals1[v - 1][g % 2])) {
                                    p = w;
                                    q = y;
                                    l = 1;
                                    break;
                                }
                            }
                        }//得到动物的位置。
                        if (l == 0) {
                            System.out.println("请输入合法指令");
                            break;
                        } else {
                            if (order1.charAt(1) == 's') {
                                if (p == 6) {
                                    System.out.println("不能超出边界！");
                                    g--;
                                    currentStep--;
                                } else {
                                    switch (table[p + 1][q]) {
                                        case " 　 ":
                                            if (p == 2 && q == 0 || p == 4 && q == 0 || p == 3 && q == 1 || p == 3 && q == 7 || p == 2 && q == 8 || p == 4 && q == 8) {
                                                table[p + 1][q] = table[p][q];
                                                table[p][q] = " 陷 ";
                                            } else if (p == 2 && q == 3 || p == 2 && q == 4 || p == 2 && q == 5 || p == 5 && q == 3 || p == 5 && q == 4 || p == 5 && q == 5) {
                                                table[p + 1][q] = table[p][q];
                                                table[p][q] = " 水 ";
                                            }//还原水域及陷阱。
                                            else {
                                                table[p + 1][q] = table[p][q];
                                                table[p][q] = " 　 ";
                                            }
                                            break;
                                        case " 家 ":
                                            if (g % 2 == 0) {
                                                if (p == 2 && q == 0) {
                                                    System.out.println("不能走自己的家");
                                                    g--;
                                                    currentStep--;
                                                    break;
                                                } else {
                                                    table[p + 1][q] = table[p][q];
                                                    table[p][q] = " 陷 ";
                                                    System.out.println("左方赢了，游戏结束。输入restart重新开始。");
                                                    h = 100;
                                                    break;
                                                }
                                            }
                                            if (g % 2 == 1) {
                                                if (p == 2 && q == 8) {
                                                    System.out.println("不能走自己的家");
                                                    g--;
                                                    currentStep--;
                                                    break;
                                                } else {
                                                    table[p + 1][q] = table[p][q];
                                                    table[p][q] = " 陷 ";
                                                    System.out.println("右方赢了，游戏结束。输入restart重新开始。");
                                                    h = 100;
                                                    break;
                                                }
                                            }//胜负判断一。
                                        case " 陷 ":
                                            table[p + 1][q] = table[p][q];
                                            table[p][q] = " 　 ";
                                            break;
                                        case " 水 ":
                                            if (v == 1) {
                                                if (p == 1 && q == 3 || p == 1 && q == 4 || p == 1 && q == 5 || p == 4 && q == 3 || p == 4 && q == 4 || p == 4 && q == 5) {
                                                    table[p + 1][q] = table[p][q];
                                                    table[p][q] = " 水 ";
                                                } else {
                                                    table[p + 1][q] = table[p][q];
                                                    table[p][q] = " 　 ";
                                                }
                                            } else if (v == 6 || v == 7) {
                                                if (table[p + 1][q] == animals1[0][1 - g % 2] || table[p + 2][q] == animals1[0][1 - g % 2]) {
                                                    System.out.println("对方老鼠在中间，不能跳河！");
                                                    g--;
                                                    currentStep--;
                                                } else if (table[p + 3][q] == animals1[v][1 - g % 2] || table[p + 3][q] == animals1[v + 1][1 - g % 2]) {
                                                    System.out.println("对方动物高于己方，不能跳河！");
                                                    g--;
                                                    currentStep--;
                                                } else if (table[p + 3][q] == animals1[7][g % 2] || table[p + 3][q] == animals1[6][g % 2] || table[p + 3][q] == animals1[5][g % 2] || table[p + 3][q] == animals1[4][g % 2]) {
                                                    System.out.println("对面有己方动物，不能跳河！");
                                                    g--;
                                                    currentStep--;
                                                } else if (table[p + 3][q] == animals1[3][g % 2] || table[p + 3][q] == animals1[2][g % 2] || table[p + 3][q] == animals1[1][g % 2] || table[p + 3][q] == animals1[0][g % 2]) {
                                                    System.out.println("对面有己方动物，不能跳河！");
                                                    g--;
                                                    currentStep--;
                                                } else {
                                                    table[p + 3][q] = table[p][q];
                                                    table[p][q] = " 　 ";
                                                }
                                            } else {
                                                System.out.println(animals[v - 1] + "不能下水！");
                                                g--;
                                                currentStep--;
                                            }//狮虎面对水时的跳河方法。
                                            break;
                                        default:
                                            if (table[p + 1][q].equals(animals1[0][g % 2])) {
                                                if (p == 0 && q == 3 || p == 0 && q == 4 || p == 0 && q == 5 || p == 3 && q == 3 || p == 3 && q == 4 || p == 3 && q == 5) {
                                                    if (v == 6 || v == 7) {
                                                        if (table[p + 1][q] == animals1[0][1 - g % 2] || table[p + 2][q] == animals1[0][1 - g % 2]) {
                                                            System.out.println("对方老鼠在中间，不能跳河！");
                                                            g--;
                                                            currentStep--;
                                                            break;
                                                        } else if (table[p + 3][q] == animals1[v + 1][1 - g % 2] || table[p + 3][q] == animals1[v][1 - g % 2]) {
                                                            System.out.println("对方动物高于己方，不能跳河！");
                                                            g--;
                                                            currentStep--;
                                                            break;
                                                        } else if (table[p + 3][q] == animals1[7][g % 2] || table[p + 3][q] == animals1[6][g % 2] || table[p + 3][q] == animals1[5][g % 2] || table[p + 3][q] == animals1[4][g % 2]) {
                                                            System.out.println("对面有己方动物，不能跳河！");
                                                            g--;
                                                            currentStep--;
                                                            break;
                                                        } else if (table[p + 3][q] == animals1[3][g % 2] || table[p + 3][q] == animals1[2][g % 2] || table[p + 3][q] == animals1[1][g % 2] || table[p + 3][q] == animals1[0][g % 2]) {
                                                            System.out.println("对面有己方动物，不能跳河！");
                                                            g--;
                                                            currentStep--;
                                                            break;
                                                        } else {
                                                            table[p + 3][q] = table[p][q];
                                                            table[p][q] = " 　 ";
                                                            break;
                                                        }
                                                    }
                                                }
                                            }//狮虎面对己方老鼠的跳河方法。
                                            else if (table[p + 1][q].equals(animals1[0][1 - g % 2])) {
                                                if (p == 0 && q == 3 || p == 0 && q == 4 || p == 0 && q == 5 || p == 3 && q == 3 || p == 3 && q == 4 || p == 3 && q == 5) {
                                                    if (v == 8) {
                                                        System.out.println("象不能吃老鼠，也不能下水！");
                                                        g--;
                                                        currentStep--;
                                                        break;
                                                    } else if (v != 1) {
                                                        System.out.println("不能吃水里的老鼠！");
                                                        g--;
                                                        currentStep--;
                                                        break;
                                                    }
                                                }
                                                if (p == 1 && q == 3 || p == 1 && q == 4 || p == 1 && q == 5 || p == 4 && q == 3 || p == 4 && q == 4 || p == 4 && q == 5) {
                                                    if (v == 1) {
                                                        table[p + 1][q] = table[p][q];
                                                        table[p][q] = " 水 ";
                                                        break;
                                                    }
                                                }
                                            }//对水中老鼠的吃法。
                                            if (v == 1) {
                                                if (table[p + 1][q].equals(animals1[7][1 - g % 2])) {
                                                    if (p == 2 && q == 3 || p == 2 && q == 4 || p == 2 && q == 5 || p == 5 && q == 3 || p == 5 && q == 4 || p == 5 && q == 5) {
                                                        System.out.println("水里的老鼠不能攻击大象！");
                                                        g--;
                                                        currentStep--;
                                                        break;
                                                    } else {
                                                        table[p + 1][q] = table[p][q];
                                                        table[p][q] = " 　 ";
                                                        break;
                                                    }
                                                }
                                                if (table[p + 1][q].equals(animals1[0][1 - g % 2])) {
                                                    if (p == 2 && q == 3 || p == 2 && q == 4 || p == 2 && q == 5 || p == 5 && q == 3 || p == 5 && q == 4 || p == 5 && q == 5) {
                                                        table[p + 1][q] = table[p][q];
                                                        table[p][q] = " 水 ";
                                                        break;
                                                    }
                                                }
                                            }//水中老鼠对陆上动物的吃法。
                                            else if (v == 8) {
                                                if (table[p + 1][q].equals(animals1[0][1]) && g == 0 || table[p + 1][q].equals(animals1[0][0]) && g == 1) {
                                                    System.out.println("不能送死！");
                                                    g--;
                                                    currentStep--;
                                                    break;
                                                }
                                            }
                                            for (int k = 0; k < 8; k++) {
                                                if (table[p + 1][q].equals(animals1[k][g % 2])) {
                                                    System.out.println("不能进入友方单位格子！");
                                                    g--;
                                                    currentStep--;
                                                    break;
                                                } else if (table[p + 1][q].equals(animals1[k][1 - g % 2])) {
                                                    if (g % 2 == 1) {
                                                        if (p == 2 && q == 7 || p == 1 && q == 8 || p == 3 && q == 8) {
                                                            table[p + 1][q] = table[p][q];
                                                            table[p][q] = " 　 ";
                                                            break;
                                                        } else if (table[p][q].charAt(2) >= table[p + 1][q].charAt(0)) {
                                                            table[p + 1][q] = table[p][q];
                                                            table[p][q] = " 　 ";
                                                            break;
                                                        } else if (table[p][q].charAt(2) < table[p + 1][q].charAt(0)) {
                                                            System.out.println("不能送死！");
                                                            g--;
                                                            currentStep--;
                                                            break;
                                                        }
                                                    }
                                                    if (g % 2 == 0) {
                                                        if (p == 1 && q == 0 || p == 3 && q == 0 || p == 2 && q == 1) {
                                                            table[p + 1][q] = table[p][q];
                                                            table[p][q] = " 　 ";
                                                            break;
                                                        } else if (table[p][q].charAt(0) >= table[p + 1][q].charAt(2)) {
                                                            table[p + 1][q] = table[p][q];
                                                            table[p][q] = " 　 ";
                                                            break;
                                                        } else if (table[p][q].charAt(0) < table[p + 1][q].charAt(2)) {
                                                            System.out.println("不能送死！");
                                                            g--;
                                                            currentStep--;
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                    }
                                }
                            } else if (order1.charAt(1) == 'd') {
                                if (q == 8) {
                                    System.out.println("不能超出边界！");
                                    g--;
                                    currentStep--;
                                } else {
                                    switch (table[p][q + 1]) {
                                        case " 　 ":
                                            if (p == 2 && q == 0 || p == 4 && q == 0 || p == 3 && q == 1 || p == 3 && q == 7 || p == 2 && q == 8 || p == 4 && q == 8) {
                                                table[p][q + 1] = table[p][q];
                                                table[p][q] = " 陷 ";//还原陷阱。
                                            } else if (p == 1 && q == 5 || p == 2 && q == 5 || p == 4 && q == 5 || p == 5 && q == 5) {
                                                table[p][q + 1] = table[p][q];
                                                table[p][q] = " 水 ";//还原水域。
                                            } else {
                                                table[p][q + 1] = table[p][q];
                                                table[p][q] = " 　 ";
                                            }//正常走法。
                                            break;
                                        case " 家 ":
                                            if (g % 2 == 0) {
                                                table[p][q + 1] = table[p][q];
                                                table[p][q] = " 陷 ";
                                                System.out.println("左方赢了，游戏结束。输入restart重新开始。");
                                                h = 100;
                                                break;
                                            }
                                            if (g % 2 == 1) {
                                                if (p == 3 && q == 7) {
                                                    System.out.println("不能走自己的家");
                                                    g--;
                                                    currentStep--;
                                                    break;
                                                } else {
                                                    table[p][q + 1] = table[p][q];
                                                    table[p][q] = " 陷 ";
                                                    System.out.println("右方赢了，游戏结束。输入restart重新开始。");
                                                    h = 100;
                                                    break;
                                                }
                                            }//胜负判断一。
                                        case " 陷 ":
                                            table[p][q + 1] = table[p][q];
                                            table[p][q] = " 　 ";
                                            break;
                                        case " 水 ":
                                            if (v == 1) {
                                                if (p == 1 && q == 4 || p == 1 && q == 3 || p == 2 && q == 3 || p == 2 && q == 4 || p == 4 && q == 3 || p == 4 && q == 4 || p == 5 && q == 3 || p == 5 && q == 4) {
                                                    table[p][q + 1] = table[p][q];
                                                    table[p][q] = " 水 ";
                                                } else {
                                                    table[p][q + 1] = table[p][q];
                                                    table[p][q] = " 　 ";
                                                }
                                            }//老鼠过河。
                                            else if (v == 6 || v == 7) {
                                                if (table[p][q + 1] == animals1[0][1 - g % 2] || table[p][q + 2] == animals1[0][1 - g % 2] || table[p][q + 3] == animals1[0][1 - g % 2]) {
                                                    System.out.println("对方老鼠在中间，不能跳河！");
                                                    g--;
                                                    currentStep--;
                                                } else if (table[p][q + 4] == animals1[v][1 - g % 2] || table[p][q + 4] == animals1[v + 1][1 - g % 2]) {
                                                    System.out.println("对方动物高于己方，不能跳河！");
                                                    g--;
                                                    currentStep--;
                                                } else if (table[p][q + 4] == animals1[7][g % 2] || table[p][q + 4] == animals1[6][g % 2] || table[p][q + 4] == animals1[5][g % 2] || table[p][q + 4] == animals1[4][g % 2]) {
                                                    System.out.println("对面有己方动物，不能跳河！");
                                                    g--;
                                                    currentStep--;
                                                } else if (table[p][q + 4] == animals1[3][g % 2] || table[p][q + 4] == animals1[2][g % 2] || table[p][q + 4] == animals1[1][g % 2] || table[p][q + 4] == animals1[0][g % 2]) {
                                                    System.out.println("对面有己方动物，不能跳河！");
                                                    g--;
                                                    currentStep--;
                                                } else {
                                                    table[p][q + 4] = table[p][q];
                                                    table[p][q] = " 　 ";
                                                }//狮虎跳河。
                                            } else {
                                                System.out.println(animals[v - 1] + "不能下水！");
                                                g--;
                                                currentStep--;
                                            }
                                            break;
                                        default:
                                            if (table[p][q + 1].equals(animals1[0][g % 2])) {
                                                if (p == 1 && q == 2 || p == 2 && q == 2 || p == 4 && q == 2 || p == 5 && q == 2) {
                                                    if (v == 6 || v == 7) {
                                                        if (table[p][q + 1] == animals1[0][1 - g % 2] || table[p][q + 2] == animals1[0][1 - g % 2] || table[p][q + 3] == animals1[0][1 - g % 2]) {
                                                            System.out.println("对方老鼠在中间，不能跳河！");
                                                            g--;
                                                            currentStep--;
                                                            break;
                                                        } else if (table[p][q + 4] == animals1[v + 1][1 - g % 2] || table[p][q + 4] == animals1[v][1 - g % 2]) {
                                                            System.out.println("对方动物高于己方，不能跳河！");
                                                            g--;
                                                            currentStep--;
                                                            break;
                                                        } else if (table[p][q + 4] == animals1[7][g % 2] || table[p][q + 4] == animals1[6][g % 2] || table[p][q + 4] == animals1[5][g % 2] || table[p][q + 4] == animals1[4][g % 2]) {
                                                            System.out.println("对面有己方动物，不能跳河！");
                                                            g--;
                                                            currentStep--;
                                                            break;
                                                        } else if (table[p][q + 4] == animals1[3][g % 2] || table[p][q + 4] == animals1[2][g % 2] || table[p][q + 4] == animals1[1][g % 2] || table[p][q + 4] == animals1[0][g % 2]) {
                                                            System.out.println("对面有己方动物，不能跳河！");
                                                            g--;
                                                            currentStep--;
                                                            break;
                                                        } else {
                                                            table[p][q + 4] = table[p][q];
                                                            table[p][q] = " 　 ";
                                                            break;
                                                        }//狮虎跳河。
                                                    }
                                                }
                                            }
                                            if (table[p][q + 1].equals(animals1[0][1 - g % 2])) {
                                                if (p == 1 && q == 2 || p == 2 && q == 2 || p == 4 && q == 2 || p == 5 && q == 2) {
                                                    if (v == 8) {
                                                        System.out.println("象不能吃老鼠，也不能下水！");
                                                        g--;
                                                        currentStep--;
                                                        break;
                                                    } else if (v != 1) {
                                                        System.out.println("不能吃水里的老鼠！");
                                                        g--;
                                                        currentStep--;
                                                        break;
                                                    }
                                                }
                                                if (p == 1 && q == 4 || p == 1 && q == 3 || p == 2 && q == 3 || p == 2 && q == 4 || p == 4 && q == 3 || p == 4 && q == 4 || p == 5 && q == 3 || p == 5 && q == 4) {
                                                    if (v == 1) {
                                                        table[p][q + 1] = table[p][q];
                                                        table[p][q] = " 水 ";
                                                        break;
                                                    }
                                                }
                                            }//判断相邻格子有对方老鼠时的走法。
                                            if (v == 1) {
                                                if (table[p][q + 1].equals(animals1[7][1 - g % 2])) {
                                                    if (p == 1 && q == 5 || p == 2 && q == 5 || p == 4 && q == 5 || p == 5 && q == 5) {
                                                        System.out.println("水里的老鼠不能攻击大象！");
                                                        g--;
                                                        currentStep--;
                                                    } else {
                                                        table[p][q + 1] = table[p][q];
                                                        table[p][q] = " 　 ";
                                                        break;
                                                    }
                                                }
                                                if (table[p][q + 1].equals(animals1[0][1 - g % 2])) {
                                                    if (p == 1 && q == 5 || p == 2 && q == 5 || p == 4 && q == 5 || p == 5 && q == 5) {
                                                        table[p][q + 1] = table[p][q];
                                                        table[p][q] = " 水 ";
                                                        break;  //区分水中的情况。
                                                    }
                                                }
                                            } else if (v == 8) {
                                                if (table[p][q + 1].equals(animals1[0][1]) && g == 0 || table[p][q + 1].equals(animals1[0][0]) && g == 1) {
                                                    System.out.println("不能送死！");
                                                    g--;
                                                    currentStep--;
                                                    break;
                                                }
                                            }//判断鼠象的特殊吃法。
                                            for (int k = 0; k < 8; k++) {
                                                if (table[p][q + 1].equals(animals1[k][g % 2])) {
                                                    System.out.println("不能进入友方单位格子！");
                                                    g--;
                                                    currentStep--;
                                                    break;
                                                }//判断相邻格子有己方动物的情况。
                                                else if (table[p][q + 1].equals(animals1[k][1 - g % 2])) {
                                                    if (g % 2 == 1) {
                                                        if (p == 3 && q == 6 || p == 2 && q == 7 || p == 4 && q == 7) {
                                                            table[p][q + 1] = table[p][q];
                                                            table[p][q] = " 　 ";
                                                            break;
                                                        }//判断是否存在陷阱。
                                                        else if (table[p][q].charAt(2) >= table[p][q + 1].charAt(0)) {
                                                            table[p][q + 1] = table[p][q];
                                                            table[p][q] = " 　 ";
                                                            break;
                                                        } else if (table[p][q].charAt(2) < table[p][q + 1].charAt(0)) {
                                                            System.out.println("不能送死！");
                                                            g--;
                                                            currentStep--;
                                                            break;
                                                        }
                                                    }//正常的吃法，即大吃小。
                                                    if (g % 2 == 0) {//因左方不可能向右走入陷阱，此处省略
                                                        if (table[p][q].charAt(0) >= table[p][q + 1].charAt(2)) {
                                                            table[p][q + 1] = table[p][q];
                                                            table[p][q] = " 　 ";
                                                            break;
                                                        } else if (table[p][q].charAt(0) < table[p][q + 1].charAt(2)) {
                                                            System.out.println("不能送死！");
                                                            g--;
                                                            currentStep--;
                                                            break;
                                                        }//正常的吃法，即大吃小。
                                                    }
                                                }
                                            }
                                    }
                                }
                            } else if (order1.charAt(1) == 'w') {
                                if (p == 0) {
                                    System.out.println("不能超出边界！");
                                    g--;
                                    currentStep--;
                                } else {
                                    switch (table[p - 1][q]) {
                                        case " 　 ":
                                            if (p == 2 && q == 0 || p == 4 && q == 0 || p == 3 && q == 1 || p == 3 && q == 7 || p == 2 && q == 8 || p == 4 && q == 8) {
                                                table[p - 1][q] = table[p][q];
                                                table[p][q] = " 陷 ";
                                            } else if (p == 1 && q == 3 || p == 1 && q == 4 || p == 1 && q == 5 || p == 4 && q == 3 || p == 4 && q == 4 || p == 4 && q == 5) {
                                                table[p - 1][q] = table[p][q];
                                                table[p][q] = " 水 ";
                                            } else {
                                                table[p - 1][q] = table[p][q];
                                                table[p][q] = " 　 ";
                                            }//还原水及陷阱；
                                            break;
                                        case " 家 ":
                                            if (g % 2 == 0) {
                                                if (p == 4 && q == 0) {
                                                    System.out.println("不能走自己的家");
                                                    g--;
                                                    currentStep--;
                                                    break;
                                                } else {
                                                    table[p - 1][q] = table[p][q];
                                                    table[p][q] = " 陷 ";
                                                    System.out.println("左方赢了，游戏结束。输入restart重新开始。");
                                                    h = 100;
                                                    break;
                                                }
                                            }
                                            if (g % 2 == 1) {
                                                if (p == 4 && q == 8) {
                                                    System.out.println("不能走自己的家");
                                                    g--;
                                                    currentStep--;
                                                    break;
                                                } else {
                                                    table[p - 1][q] = table[p][q];
                                                    table[p][q] = " 陷 ";
                                                    System.out.println("右方赢了，游戏结束。输入restart重新开始。");
                                                    h = 100;
                                                    break;
                                                }
                                            }//胜负判断。
                                        case " 陷 ":
                                            table[p - 1][q] = table[p][q];
                                            table[p][q] = " 　 ";
                                            break;
                                        case " 水 ":
                                            if (v == 1) {
                                                if (p == 2 && q == 3 || p == 2 && q == 4 || p == 2 && q == 5 || p == 5 && q == 3 || p == 5 && q == 4 || p == 5 && q == 5) {
                                                    table[p - 1][q] = table[p][q];
                                                    table[p][q] = " 水 ";
                                                } else {
                                                    table[p - 1][q] = table[p][q];
                                                    table[p][q] = " 　 ";
                                                }
                                            } else if (v == 6 || v == 7) {
                                                if (table[p - 1][q] == animals1[0][1 - g % 2] || table[p - 2][q] == animals1[0][1 - g % 2]) {
                                                    System.out.println("对方老鼠在中间，不能跳河！");
                                                    g--;
                                                    currentStep--;
                                                } else if (table[p - 3][q] == animals1[v + 1][1 - g % 2] || table[p - 3][q] == animals1[v][1 - g % 2]) {
                                                    System.out.println("对方动物高于己方，不能跳河！");
                                                    g--;
                                                    currentStep--;
                                                } else if (table[p - 3][q] == animals1[7][g % 2] || table[p - 3][q] == animals1[6][g % 2] || table[p - 3][q] == animals1[5][g % 2] || table[p - 3][q] == animals1[4][g % 2]) {
                                                    System.out.println("对面有己方动物，不能跳河！");
                                                    g--;
                                                    currentStep--;
                                                } else if (table[p - 3][q] == animals1[3][g % 2] || table[p - 3][q] == animals1[2][g % 2] || table[p - 3][q] == animals1[1][g % 2] || table[p - 3][q] == animals1[0][g % 2]) {
                                                    System.out.println("对面有己方动物，不能跳河！");
                                                    g--;
                                                    currentStep--;
                                                } else {
                                                    table[p - 3][q] = table[p][q];
                                                    table[p][q] = " 　 ";
                                                }
                                            } else {
                                                System.out.println(animals[v - 1] + "不能下水！");
                                                g--;
                                                currentStep--;
                                            }//狮虎跳河。
                                            break;
                                        default:
                                            if (table[p - 1][q].equals(animals1[0][g % 2])) {
                                                if (p == 3 && q == 3 || p == 3 && q == 4 || p == 3 && q == 5 || p == 6 && q == 3 || p == 6 && q == 4 || p == 6 && q == 5) {
                                                    if (v == 6 || v == 7) {
                                                        if (table[p - 1][q] == animals1[0][1 - g % 2] || table[p - 2][q] == animals1[0][1 - g % 2]) {
                                                            System.out.println("对方老鼠在中间，不能跳河！");
                                                            g--;
                                                            currentStep--;
                                                            break;
                                                        } else if (table[p - 3][q] == animals1[v][1 - g % 2] || table[p - 3][q] == animals1[v + 1][1 - g % 2]) {
                                                            System.out.println("对方动物高于己方，不能跳河！");
                                                            g--;
                                                            currentStep--;
                                                            break;
                                                        } else if (table[p - 3][q] == animals1[7][g % 2] || table[p - 3][q] == animals1[6][g % 2] || table[p - 3][q] == animals1[5][g % 2] || table[p - 3][q] == animals1[4][g % 2]) {
                                                            System.out.println("对面有己方动物，不能跳河！");
                                                            g--;
                                                            currentStep--;
                                                            break;
                                                        } else if (table[p - 3][q] == animals1[3][g % 2] || table[p - 3][q] == animals1[2][g % 2] || table[p - 3][q] == animals1[1][g % 2] || table[p - 3][q] == animals1[0][g % 2]) {
                                                            System.out.println("对面有己方动物，不能跳河！");
                                                            g--;
                                                            currentStep--;
                                                            break;
                                                        } else {
                                                            table[p - 3][q] = table[p][q];
                                                            table[p][q] = " 　 ";
                                                            break;
                                                        }
                                                    }
                                                }
                                            }//狮虎跳河。
                                            if (table[p - 1][q].equals(animals1[0][1 - g % 2])) {
                                                if (p == 3 && q == 3 || p == 3 && q == 4 || p == 3 && q == 5 || p == 6 && q == 3 || p == 6 && q == 4 || p == 6 && q == 5) {
                                                    if (v == 8) {
                                                        System.out.println("象不能吃老鼠，也不能下水！");
                                                        g--;
                                                        currentStep--;
                                                        break;
                                                    } else if (v != 1) {
                                                        System.out.println("不能吃水里的老鼠！");
                                                        g--;
                                                        currentStep--;
                                                        break;
                                                    } else if (v == 1) {
                                                        if (p == 2 && q == 3 || p == 2 && q == 4 || p == 2 && q == 5 || p == 5 && q == 3 || p == 5 && q == 4 || p == 5 && q == 5) {
                                                            table[p - 1][q] = table[p][q];
                                                            table[p][q] = " 水 ";
                                                            break;
                                                        }
                                                    }
                                                }
                                            }//吃水中老鼠。
                                            if (v == 1) {
                                                if (table[p - 1][q].equals(animals1[7][1 - g % 2])) {
                                                    if (p == 1 && q == 3 || p == 1 && q == 4 || p == 1 && q == 5 || p == 4 && q == 3 || p == 4 && q == 4 || p == 4 && q == 5) {
                                                        System.out.println("水里的老鼠不能攻击大象！");
                                                        g--;
                                                        currentStep--;
                                                        break;
                                                    } else {
                                                        table[p - 1][q] = table[p][q];
                                                        table[p][q] = " 　 ";
                                                        break;
                                                    }
                                                }
                                                if (table[p - 1][q].equals(animals1[0][1 - g % 2])) {
                                                    if (p == 1 && q == 3 || p == 1 && q == 4 || p == 1 && q == 5 || p == 5 && q == 3 || p == 4 && q == 4 || p == 4 && q == 5) {
                                                        table[p - 1][q] = table[p][q];
                                                        table[p][q] = " 水 ";
                                                        break;
                                                    }
                                                }
                                            }//水中老鼠的吃法。
                                            else if (v == 8) {
                                                if (table[p - 1][q].equals(animals1[0][1]) && g == 0 || table[p - 1][q].equals(animals1[0][0]) && g == 1) {
                                                    System.out.println("不能送死！");
                                                    g--;
                                                    currentStep--;
                                                    break;
                                                }
                                            }

                                            for (int k = 0; k < 8; k++) {
                                                if (table[p - 1][q].equals(animals1[k][g % 2])) {
                                                    System.out.println("不能进入友方单位格子！");
                                                    g--;
                                                    currentStep--;
                                                    break;
                                                } else if (table[p - 1][q].equals(animals1[k][1 - g % 2])) {
                                                    if (g % 2 == 1) {
                                                        if (p == 4 && q == 7 || p == 3 && q == 8 || p == 5 && q == 8) {
                                                            table[p - 1][q] = table[p][q];
                                                            table[p][q] = " 　 ";
                                                            break;
                                                        } else if (table[p][q].charAt(2) >= table[p - 1][q].charAt(0)) {
                                                            table[p - 1][q] = table[p][q];
                                                            table[p][q] = " 　 ";
                                                            break;
                                                        } else if (table[p - 1][q].charAt(2) < table[p - 1][q].charAt(0)) {
                                                            System.out.println("不能送死！");
                                                            g--;
                                                            currentStep--;
                                                            break;
                                                        }
                                                    }
                                                    if (g % 2 == 0) {
                                                        if (p == 3 && q == 0 || p == 5 && q == 0 || p == 4 && q == 1) {
                                                            table[p - 1][q] = table[p][q];
                                                            table[p][q] = " 　 ";
                                                            break;
                                                        } else if (table[p - 1][q].equals(animals1[0][1])) {
                                                            System.out.println("不能送死！");
                                                            g--;
                                                            currentStep--;
                                                            break;
                                                        } else if (table[p][q].charAt(0) >= table[p - 1][q].charAt(2)) {
                                                            table[p - 1][q] = table[p][q];
                                                            table[p][q] = " 　 ";
                                                            break;
                                                        } else if (table[p][q].charAt(0) < table[p - 1][q].charAt(2)) {
                                                            System.out.println("不能送死！");
                                                            g--;
                                                            currentStep--;
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                    }
                                }
                            } else if (order1.charAt(1) == 'a') {
                                if (q == 0) {
                                    System.out.println("不能超出边界！");
                                    g--;
                                } else {
                                    switch (table[p][q - 1]) {
                                        case " 　 ":
                                            if (p == 2 && q == 0 || p == 4 && q == 0 || p == 3 && q == 1 || p == 3 && q == 7 || p == 2 && q == 8 || p == 4 && q == 8) {
                                                table[p][q - 1] = table[p][q];
                                                table[p][q] = " 陷 ";
                                            } else if (p == 1 && q == 3 || p == 2 && q == 3 || p == 4 && q == 3 || p == 5 && q == 3) {
                                                table[p][q - 1] = table[p][q];
                                                table[p][q] = " 水 ";
                                            } else {
                                                table[p][q - 1] = table[p][q];
                                                table[p][q] = " 　 ";
                                            }//还原水及陷阱；
                                            break;
                                        case " 家 ":
                                            if (g % 2 == 0) {
                                                if (p == 3 && q == 1) {
                                                    System.out.println("不能走自己的家");
                                                    g--;
                                                    currentStep--;
                                                    break;
                                                } else {
                                                    table[p][q - 1] = table[p][q];
                                                    table[p][q] = " 陷 ";
                                                    System.out.println("左方赢了，游戏结束。输入restart重新开始。");
                                                    break;
                                                }
                                            }
                                            if (g % 2 == 1) {
                                                table[p][q - 1] = table[p][q];
                                                table[p][q] = " 陷 ";
                                                System.out.println("右方赢了，游戏结束输入restart重新开始。。");
                                                break;
                                            }//胜负判断。
                                        case " 陷 ":
                                            table[p][q - 1] = table[p][q];
                                            table[p][q] = " 　 ";
                                            break;
                                        case " 水 ":
                                            if (v == 1) {
                                                if (p == 1 && q == 4 || p == 1 && q == 5 || p == 2 && q == 5 || p == 2 && q == 4 || p == 4 && q == 5 || p == 4 && q == 4 || p == 5 && q == 5 || p == 5 && q == 4) {
                                                    table[p][q - 1] = table[p][q];
                                                    table[p][q] = " 水 ";
                                                } else {
                                                    table[p][q - 1] = table[p][q];
                                                    table[p][q] = " 　 ";
                                                }
                                            } else if (v == 6 || v == 7) {
                                                if (table[p][q - 1] == animals1[0][1 - g % 2] && table[p][q - 2] == animals1[0][1 - g % 2] && table[p][q - 3] == animals1[0][1 - g % 2]) {
                                                    System.out.println("对方老鼠在中间，不能跳河！");
                                                    g--;
                                                    currentStep--;
                                                } else if (table[p][q - 4] == animals1[v][1 - g % 2] || table[p][q - 4] == animals1[v + 1][1 - g % 2]) {
                                                    System.out.println("对方动物高于己方，不能跳河！");
                                                    g--;
                                                    currentStep--;
                                                } else if (table[p][q - 4] == animals1[7][g % 2] || table[p][q - 4] == animals1[6][g % 2] || table[p][q - 4] == animals1[5][g % 2] || table[p][q - 4] == animals1[4][g % 2]) {
                                                    System.out.println("对面有己方动物，不能跳河！");
                                                    g--;
                                                    currentStep--;
                                                } else if (table[p][q - 4] == animals1[3][g % 2] || table[p][q - 4] == animals1[2][g % 2] || table[p][q - 4] == animals1[1][g % 2] || table[p][q - 4] == animals1[0][g % 2]) {
                                                    System.out.println("对面有己方动物，不能跳河！");
                                                    g--;
                                                    currentStep--;
                                                } else {
                                                    table[p][q - 4] = table[p][q];
                                                    table[p][q] = " 　 ";
                                                }
                                            } else {
                                                System.out.println(animals[v - 1] + "不能下水！");
                                                g--;
                                                currentStep--;
                                            }//狮虎跳河。
                                            break;
                                        default:
                                            if (table[p][q - 1].equals(animals1[0][g % 2])) {
                                                if (p == 1 && q == 6 || p == 0 && q == 6 || p == 4 && q == 6 || p == 5 && q == 6) {
                                                    if (v == 6 || v == 7) {
                                                        if (table[p][q - 1] == animals1[0][1 - g % 2] && table[p][q - 2] == animals1[0][1 - g % 2] && table[p][q - 3] == animals1[0][1 - g % 2]) {
                                                            System.out.println("对方老鼠在中间，不能跳河！");
                                                            g--;
                                                            currentStep--;
                                                            break;
                                                        } else if (table[p][q - 4] == animals1[v + 1][1 - g % 2] || table[p][q - 4] == animals1[v][1 - g % 2]) {
                                                            System.out.println("对方动物高于己方，不能跳河！");
                                                            g--;
                                                            break;
                                                        } else if (table[p][q - 4] == animals1[7][g % 2] || table[p][q - 4] == animals1[6][g % 2] || table[p][q - 4] == animals1[5][g % 2] || table[p][q - 4] == animals1[4][g % 2]) {
                                                            System.out.println("对面有己方动物，不能跳河！");
                                                            g--;
                                                            currentStep--;
                                                            break;
                                                        } else if (table[p][q - 4] == animals1[3][g % 2] || table[p][q - 4] == animals1[2][g % 2] || table[p][q - 4] == animals1[1][g % 2] || table[p][q - 4] == animals1[0][g % 2]) {
                                                            System.out.println("对面有己方动物，不能跳河！");
                                                            g--;
                                                            currentStep--;
                                                            break;
                                                        } else {
                                                            table[p][q - 4] = table[p][q];
                                                            table[p][q] = " 　 ";
                                                            break;
                                                        }
                                                    }
                                                }
                                            }//狮虎跳河。
                                            if (table[p][q - 1].equals(animals1[0][1 - g % 2])) {
                                                if (p == 1 && q == 6 || p == 2 && q == 6 || p == 4 && q == 6 || p == 5 && q == 6) {
                                                    if (v == 8) {
                                                        g--;
                                                        break;
                                                    } else if (v != 1) {
                                                        System.out.println("不能吃水里的老鼠！");
                                                        g--;
                                                        currentStep--;
                                                        break;
                                                    }
                                                } else if (p == 1 && q == 4 || p == 1 && q == 5 || p == 2 && q == 5 || p == 2 && q == 4 || p == 4 && q == 5 || p == 4 && q == 4 || p == 5 && q == 5 || p == 5 && q == 4) {
                                                    if (v == 1) {
                                                        table[p][q - 1] = table[p][q];
                                                        table[p][q] = " 水 ";
                                                        break;
                                                    }
                                                }
                                            }//吃水中老鼠。
                                            else if (v == 1) {
                                                if (table[p][q - 1].equals(animals1[7][1 - g % 2])) {
                                                    if (p == 1 && q == 3 || p == 2 && q == 3 || p == 4 && q == 3 || p == 5 && q == 3) {
                                                        System.out.println("水里的老鼠不能攻击大象！");
                                                        g--;
                                                        currentStep--;
                                                        break;
                                                    } else {
                                                        table[p][q - 1] = table[p][q];
                                                        table[p][q] = " 　 ";
                                                        break;
                                                    }
                                                }
                                                if (table[p][q - 1].equals(animals1[0][1 - g % 2])) {
                                                    if (p == 1 && q == 3 || p == 2 && q == 3 || p == 4 && q == 3 || p == 5 && q == 3) {
                                                        table[p][q - 1] = table[p][q];
                                                        table[p][q] = " 水 ";
                                                        break;
                                                    }
                                                }
                                            }//水中老鼠攻击岸上动物。
                                            else if (v == 8) {
                                                if (table[p][q - 1].equals(animals1[0][1])&&g==0||table[p][q - 1].equals(animals1[0][0])&&g==1) {
                                                    System.out.println("不能送死！");
                                                    g--;
                                                    currentStep--;
                                                    break;
                                                }
                                            }
                                            for (int k = 0; k < 8; k++) {
                                                if (table[p][q - 1].equals(animals1[k][g % 2])) {
                                                    System.out.println("不能进入友方单位格子！");
                                                    g--;
                                                    currentStep--;
                                                    break;
                                                } else if (table[p][q - 1].equals(animals1[k][1 - g % 2])) {
                                                    if (g % 2 == 1) {
                                                        if (table[p][q].charAt(2) >= table[p][q - 1].charAt(0)) {
                                                            table[p][q - 1] = table[p][q];
                                                            table[p][q] = " 　 ";
                                                            break;
                                                        } else if (table[p][q].charAt(2) < table[p][q - 1].charAt(0)) {
                                                            System.out.println("不能送死！");
                                                            g--;
                                                            currentStep--;
                                                            break;
                                                        }
                                                    }
                                                    if (g % 2 == 0) {
                                                        if (p == 2 && q == 1 || p == 4 && q == 1 || p == 3 && q == 2) {
                                                            table[p][q - 1] = table[p][q];
                                                            table[p][q] = " 　 ";
                                                            break;
                                                        } else if (table[p][q].charAt(0) >= table[p][q - 1].charAt(1)) {
                                                            table[p][q - 1] = table[p][q];
                                                            table[p][q] = " 　 ";
                                                            break;
                                                        } else if (table[p][q].charAt(0) < table[p][q - 1].charAt(2)) {
                                                            System.out.println("不能送死！");
                                                            g--;
                                                            currentStep--;
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                    }
                                }
                            } else {
                                System.out.println("请输入正确指令");
                                currentStep--;
                                break;
                            }
                            g++;
                            currentStep++;
                            lastStep = currentStep;
                            boardHistory[currentStep] = copyBoard(table);
                        }
                    }
            }
                        for (int p = 0; p < 7; p++) {
                            for (int j = 0; j < 9; j++) {
                                System.out.print(boardHistory[currentStep][p][j]);
                            }
                            System.out.println();
                        }
                        int theNemberofanimal1 = 0, theNemberofanimal2 = 0;
                        for (int k = 0; k < 7; k++) {
                            for (int b = 0; b < 9; b++) {
                                for (int j = 0; j < 9; j++) {
                                    if (table[k][b].equals(animals1[j][0])) {
                                        theNemberofanimal1++;
                                    }
                                    if (table[k][b].equals(animals1[j][1])) {
                                        theNemberofanimal2++;
                                    }
                                }
                            }
                        }
                        if (theNemberofanimal1 == 0) {
                            System.out.print("左方兽类已被吃完，游戏结束。输入restart重新开始。");
                            h = 100;
                        }
                        if (theNemberofanimal2 == 0) {
                            System.out.print("左方兽类已被吃完，游戏结束。输入restart重新开始。");
                            h = 100;
                        }//胜负判断二。
                        if (h == 100&&r==0) {
                            Scanner input2 = new Scanner(System.in);
                            String order2 = input2.next();
                            if (order2.equals("restart")) {
                                h = 0;
                                currentStep=0;
                                g=0;
                                for (int p = 0; p < 7; p++) {
                                    for (int j = 0; j < 9; j++) {
                                        System.out.print(boardHistory[currentStep][p][j]);
                                    }
                                    System.out.println();
                                }
                                table = copyBoard( boardHistory[currentStep]);
                            }
                        }//restart功能。
            }
        }
    private static String[][] copyBoard(String[][] array) {//复制数组。
        String[][] newArray = new String[7][9];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                newArray[i][j] = array[i][j];
            }
        }
        return newArray;
    }
}









