package classes;


import java.awt.*;

import javax.swing.JPanel;

public class List extends JPanel{

    List()
    {

        GridBagConstraints layout = new GridBagConstraints();
        //layout.setVgap(5);

        //this.setLayout(layout);
        this.setPreferredSize(new Dimension(360,560));

    }

    public void updateNumbers()
    {
        Component[] listItems = this.getComponents();

        for(int i = 0;i<listItems.length;i++)
        {
            if(listItems[i] instanceof Task)
            {
                ((Task)listItems[i]).changeIndex(i+1);
            }
        }

    }

    public void removeCompletedTasks()
    {

        for(Component c : getComponents())
        {
            if(c instanceof Task)
            {
                if(((Task)c).getState())
                {
                    remove(c);
                    updateNumbers();
                }
            }
        }

    }
}