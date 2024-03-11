import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game {
    private static List<List<Integer>> frames = new ArrayList<>();
    private static List<Integer> allScore = new ArrayList<>();
    private static Integer scoreActually = 0;

    public static void play(Integer frameNumber, Integer launchNumber) {
        try (Scanner scanner = new Scanner(System.in)) {
            for (int i = frameNumber; i > 0; i--) {
                List<Integer> frame = Arrays.asList(0,0,0);
                Integer remainingQuilles = 15;
                
                scoreActually = view(i, frameNumber, launchNumber, remainingQuilles, scanner, frame, scoreActually);
                
                allScore.add(scoreActually);
                
                if (frames.size() > 0) {
                    Integer frameSum = frame.stream().reduce(0, Integer::sum);
                    List<Integer> beforeFrame = frames.get((5 - frameNumber)-1);
                    Integer beforeScore = allScore.get(allScore.size()-2);
                    boolean beforeFrameStrike = beforeFrame.get(0) == 15 && beforeFrame.get(0) == 15;
                    boolean beforeFrameSpareCase1 = beforeFrame.get(0) + beforeFrame.get(1) == 15;
                    boolean beforeFrameSpareCase2 = beforeFrame.get(0) + beforeFrame.get(1) + beforeFrame.get(2) == 15;

                    if (beforeFrameStrike) {
                        allScore.set(allScore.size()-2, (beforeScore + frameSum));
                    
                    } else if (beforeFrameSpareCase1) {
                        allScore.set(allScore.size()-2, ((beforeScore - beforeFrame.get(0)) + frame.get(0) + frame.get(1)));
                    
                    } else if (beforeFrameSpareCase2) {
                        allScore.set(allScore.size()-2, ((beforeScore - beforeFrame.get(0)  - beforeFrame.get(1)) + frame.get(0) + frame.get(1) + frame.get(2)));
                    
                    }
                }
                frames.add(frame);
                switch (frames.size()) {
                    case 5:
                        i++;
                        frameNumber = 0;
                        break;
                    
                    case 6:
                        i--;
                        frameNumber = 0;
                        break;

                    default:
                        frameNumber--;
                        break;
                }
            }

            System.out.println("\t1 \t\t2 \t\t3 \t\t4 \t\t5 \t\t ");
            for (List<Integer> frame : frames) {
                System.out.print(frame + "\t");
            }
            System.out.println("\n\t"+ allScore.get(0) + "\t\t" + allScore.get(1) + "\t\t" + allScore.get(2) + "\t\t" + allScore.get(3) + "\t\t" + allScore.get(4) + "\t\t");

        } catch (Exception e) {
            e.getMessage();
        }
    }

    private static Integer view(int index, Integer frameNumber, Integer launchNumber, Integer remainingQuilles, Scanner scanner, List<Integer> frame, Integer scoreActually) {
        for (int j = launchNumber; j > 0; j--) {
            System.out.println("\nFrame number : F-" + (5 - frameNumber+1));
            System.out.println("Launch number : L-" + (3 - launchNumber+1));
            System.out.println("Rest quilles : RQ-" + remainingQuilles);
            System.out.print("Quilles touch = ");
            Integer touchQuilles = scanner.nextInt();
            
            if (touchQuilles < 0 || touchQuilles > 15) {
                System.out.println("Quilles touch invalid !!!");
                System.out.println("Retry with correct value : (Q >= 0) or (Q > 15)");
                j++;
                continue;

            } else if (touchQuilles > remainingQuilles) {
                System.out.println("Quilles touch invalid !!!");
                System.out.println("Quilles touch must be <= " + remainingQuilles);
                j++;
                continue;

            } else {
                frame.set(3 - launchNumber, touchQuilles);
                launchNumber--;

                remainingQuilles = remainingQuilles - touchQuilles;
                if (remainingQuilles == 0 && frame.stream().reduce(0, Integer::sum) == 15) {
                    scoreActually = scoreActually + 15;
                } else {
                    scoreActually = scoreActually + touchQuilles;
                }
                
                System.out.println("Score actually : " + scoreActually);
                System.out.println("--------------------");

                if (remainingQuilles == 0 || (frameNumber == 1 && j <= 1)) {
                    index++;
                    j = 0;
                    break;
                }
            }
        }
        return scoreActually;
    }
}
