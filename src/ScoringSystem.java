public class ScoringSystem {

    ScoringSystem(){ }

    int addPoints(int level, int lines){

        switch (lines){
            case 1:
                return 40*(level+1);
            case 2:
                return 100*(level+1);
            case 3:
                return 300*(level+1);
            case 4:
                return 1200*(level+1);
        }

        return 0;
    }

    int getLevel(int removedLines){

        return 0;
    }
}
