
import org.junit.*;

public class TennisGameTest {

    private TennisGame tennisGame;

    @org.junit.Before
    public void setUp() {
        tennisGame = new TennisGame(new FakePlayGame());
    }
    @Test
    public void AfterNewGameScoreZero(){
        AssertOnePlayerScore(0, 0, tennisGame.getCurrentScore().playerAScore);

    }
    @Test
    public void AfterOneGame15point(){
        addWinningGameForAPlayer(1);
        AssertOnePlayerScore(0, 15, tennisGame.getCurrentScore().playerAScore);
    }
    @Test
    public void AfterTwoGame30point(){
        addWinningGameForAPlayer(2);
        AssertOnePlayerScore(0, 30, tennisGame.getCurrentScore().playerAScore);
    }

    @Test
    public void AfterThreeGame40point(){
        addWinningGameForAPlayer(3);
        AssertOnePlayerScore(0, 40, tennisGame.getCurrentScore().playerAScore);
    }

    @Test
    public void EasyWinningSet(){
        addWinningGameForAPlayer(4);
        AssertOnePlayerScore(1, 0, tennisGame.getCurrentScore().playerAScore);
    }
    @Test
    public void FourGameToOneGameWinningSet(){
        addWinningGameForBPlayer(1);
        addWinningGameForAPlayer(1);
        addWinningGameForBPlayer(3);
        AssertOnePlayerScore(0, 15, tennisGame.getCurrentScore().playerAScore);
        AssertOnePlayerScore(1, 0, tennisGame.getCurrentScore().playerBScore);
    }

    @Test
    public void after2GameScoreEquals(){
        addAnEqualGame();
        AssertOnePlayerScore(0, 15, tennisGame.getCurrentScore().playerAScore);
        AssertOnePlayerScore(0, 15, tennisGame.getCurrentScore().playerBScore);
    }
    @Test
    public void after8GameThereIsNoWinner(){
        addWinningGameForAPlayer(3);
        addWinningGameForBPlayer(4);
        addWinningGameForAPlayer(1);
        AssertOnePlayerScore(0, 40, tennisGame.getCurrentScore().playerAScore);
        AssertOnePlayerScore(0, 40, tennisGame.getCurrentScore().playerBScore);
    }
    @Test
    public void longSetGame(){
        addWinningGameForAPlayer(3);
        addWinningGameForBPlayer(4);
        addAnEqualGame(4);
        addWinningGameForBPlayer(1);
        AssertOnePlayerScore(0, 40, tennisGame.getCurrentScore().playerAScore);
        AssertOnePlayerScore(1, 0, tennisGame.getCurrentScore().playerBScore);
    }
    @Test(expected = InvalidTennisPlayer.class)
    public void invalidPlayerGetGame(){
        tennisGame.addGame("X");
    }

    @Test
    public void WinTwoSet(){
        addWinningGameForBPlayer(4);
        tennisGame.getCurrentScore();
        addWinningGameForBPlayer(4);
        AssertOnePlayerScore(2, 0, tennisGame.getCurrentScore().playerBScore);
        AssertOnePlayerScore(0, 0, tennisGame.getCurrentScore().playerAScore);
    }
    @Test
    public void WinLongTwoSet(){
        addWinningGameForBPlayer(4);
        tennisGame.getCurrentScore();
        addWinningGameForBPlayer(4);
        addWinningGameForAPlayer(3);
        addAnEqualGame(4);
        addWinningGameForBPlayer(1);
        AssertOnePlayerScore(2, 0, tennisGame.getCurrentScore().playerBScore);
        AssertOnePlayerScore(0, 40, tennisGame.getCurrentScore().playerAScore);
    }

    @Test
    public void GameIsNotOverYet(){
        Assert.assertEquals(false, tennisGame.IsMatchOver(TypeOfTennisMatch.BestOfThree));
        addWinningGameForBPlayer(4);
        Assert.assertEquals(false, tennisGame.IsMatchOver(TypeOfTennisMatch.BestOfThree));
    }

    @Test
    public void BestOfThreeGameIsOver(){
        addWinningGameForAPlayer(4);
        tennisGame.getCurrentScore();
        addWinningGameForAPlayer(4);
        tennisGame.getCurrentScore();
        Assert.assertEquals(true, tennisGame.IsMatchOver(TypeOfTennisMatch.BestOfThree));
    }

    @Test
    public void BestOfFiveGameIsNotOver(){
        addWinningGameForAPlayer(4);
        tennisGame.getCurrentScore();
        addWinningGameForAPlayer(4);
        tennisGame.getCurrentScore();
        Assert.assertEquals(false, tennisGame.IsMatchOver(TypeOfTennisMatch.BestOfFive));
    }
    @Test
    public void BestOfFiveGameIsOver(){
        addWinningGameForAPlayer(4);
        tennisGame.getCurrentScore();
        addWinningGameForAPlayer(4);
        tennisGame.getCurrentScore();
        addWinningGameForAPlayer(4);
        tennisGame.getCurrentScore();
        Assert.assertEquals(true, tennisGame.IsMatchOver(TypeOfTennisMatch.BestOfFive));
    }
    @Test
    public void GetWinnerName(){
        addWinningGameForAPlayer(4);
        tennisGame.getCurrentScore();
        addWinningGameForAPlayer(4);
        tennisGame.getCurrentScore();
        Assert.assertEquals("A", tennisGame.GetWinner(2));
    }
    @Test
    public void GetBWinnerName(){
        addWinningGameForBPlayer(4);
        tennisGame.getCurrentScore();
        addWinningGameForBPlayer(4);
        tennisGame.getCurrentScore();
        Assert.assertEquals("B", tennisGame.GetWinner(2));
    }

    private void addAnEqualGame(int numberOfEqualGame) {
        for (int i=0; i<numberOfEqualGame; i++){
            addAnEqualGame();
        }
    }
    private void addAnEqualGame() {
        addWinningGameForAPlayer(1);
        addWinningGameForBPlayer(1);
    }

    private void AssertOnePlayerScore(int set, int game, TennisScore ts) {
        Assert.assertEquals(game, ts.game);
        Assert.assertEquals(set, ts.set);
    }

    private void addWinningGameForAPlayer(int numberOfWonGame) {
        addWinningGame(numberOfWonGame, "A");
    }

    private void addWinningGameForBPlayer(int numberOfWonGame) {
        addWinningGame(numberOfWonGame, "B");
    }

    private void addWinningGame(int numberOfWonGame, String player) {
        for (int i=0; i<numberOfWonGame; i++) {
            tennisGame.addGame(player);
        }
    }

    static class FakePlayGame implements PlayGame {
        public String GetWhoWonTheGame() {
            return "A";
        }
    }
}