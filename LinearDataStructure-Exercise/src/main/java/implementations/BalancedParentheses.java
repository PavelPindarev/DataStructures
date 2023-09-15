package implementations;

import interfaces.Solvable;

public class BalancedParentheses implements Solvable {
    private String parentheses;

    private int head;
    private int tail;

    public BalancedParentheses(String parentheses) {
        this.parentheses = parentheses;
        this.head = 0;
        this.tail = parentheses.length() - 1;
    }

    @Override
    public Boolean solve() {
        if (isSequenceOdd()) {
            return false;
        }
        int timesOfIteration = this.parentheses.length() / 2;
        for (int i = 0; i < timesOfIteration; i++) {
            char firstSymbol = this.parentheses.charAt(this.head++);
            char lastSymbol = this.parentheses.charAt(this.tail--);

            if (!checkPalindrome(firstSymbol, lastSymbol)) {
                return false;
            }

        }
        return true;
    }

    private boolean checkPalindrome(char firstSymbol, char lastSymbol) {
        switch (firstSymbol) {
            case '(':
                return firstSymbol + 1 == lastSymbol;
            case '{':
            case '[':
                return firstSymbol + 2 == lastSymbol;
        }
        return false;
    }

    private boolean isSequenceOdd() {
        return this.parentheses.length() / 2 == 1;
    }
}
