package name.abuchen.portfolio.snapshot.security;

import java.util.List;

import name.abuchen.portfolio.model.AccountTransaction;
import name.abuchen.portfolio.model.PortfolioTransaction;
import name.abuchen.portfolio.model.Transaction;

/* package */abstract class Calculation
{
    public void visit(DividendInitialTransaction t)
    {}

    public void visit(DividendFinalTransaction t)
    {}

    public void visit(DividendTransaction t)
    {}

    public void visit(PortfolioTransaction t)
    {}

    public void visit(AccountTransaction t)
    {}

    public final void visitAll(List<? extends Transaction> transactions)
    {
        for (Transaction t : transactions)
        {
            if (t instanceof DividendInitialTransaction)
                visit((DividendInitialTransaction) t);
            else if (t instanceof DividendFinalTransaction)
                visit((DividendFinalTransaction) t);
            else if (t instanceof DividendTransaction)
                visit((DividendTransaction) t);
            else if (t instanceof PortfolioTransaction)
                visit((PortfolioTransaction) t);
            else if (t instanceof AccountTransaction)
                visit((AccountTransaction) t);
            else
                throw new UnsupportedOperationException();
        }
    }

    public static <T extends Calculation> T perform(Class<T> type, List<? extends Transaction> transactions)
    {
        try
        {
            T thing = type.newInstance();
            thing.visitAll(transactions);
            return thing;
        }
        catch (Exception e)
        {
            throw new UnsupportedOperationException(e);
        }
    }
}
