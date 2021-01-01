package com.mkirilly.delayedshutdown;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Osztaly csak statikus metodus(ok)kal, amivel le lehet allitani
 * a rendszert.
 * @author Miki
 */
public class Shutdowner {
    
    /** timer that will schedule the countdown job once per minute */
    private Timer timer;
    
    /** minutes to shutdown */
    public int minutesToSd;
    
    public boolean hibernate;
    public boolean warn;
    
    public final ShutdownGui gui;
    
    /**
     * task to run every minute, counts down to 0
     */
    private class MinuteTick extends TimerTask {
        private final Shutdowner sd;
        public MinuteTick(Shutdowner sd) {
            this.sd = sd;
        }
        public void run() {
            synchronized (sd)
            {
                sd.minutesToSd--;
            
                if (sd.minutesToSd == 0)
                {
                    // never run again
                    cancel();

                    if (true)
                    {
                        StringBuilder cmdBuilder;
                        if (sd.hibernate) {
                            cmdBuilder =
                                    new StringBuilder("rundll32.exe powrprof.dll,SetSuspendState 0,1,0");
                        } else {
                            cmdBuilder =
                                    new StringBuilder("shutdown -s -t 0");
                            if (!sd.warn) {
                                cmdBuilder.append(" -f");
                            }
                        }

                        // ta-taaaa! ez lesz, amit lefuttatunk
                        final String cmd = cmdBuilder.toString();

                        // lefuttatjuk a parancsot uj folyamatban, hogy
                        // bezarhassuk az ablakot
                        java.awt.EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                try {
                                    // kapcsolodunk az operacios rendszerhez
                                    Runtime r = Runtime.getRuntime();
                                    // vegrehajtjuk a parancsot
                                    r.exec(cmd);

                                } catch (IOException ex) {
                                    Logger.getLogger(Shutdowner.class.getName())
                                            .log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                    }
                    else
                    {
                        // debug code
                        if (sd.hibernate) {
                            System.out.println("Hibernating!!!!");
                        } else {
                            System.out.println("Shutting down!!!!");
                        }
                    }
                }
                
                // notify GUI
                gui.minCountdown(sd.minutesToSd);
            }
        }
    }
    
    public Shutdowner(ShutdownGui g) {
        gui = g;
    }
    /**
     * Alljon le a rendszer valahany perc mulva
     * @param minutes hany perc mulva alljon le a rendszer
     * @param hibernate hibernal (nem sima leallas)
     * @param warn figyelmeztessen, mielott leall
     */
    public void sdInMinutes(int minutes, boolean hibernate, boolean warn)
    {
        if (minutes > 0)
        {
            // save parameters
            synchronized (this) {
                this.minutesToSd = minutes;
                this.hibernate = hibernate;
                this.warn = warn;
            }
            
            // create and start timer
            timer = new Timer();
            
            // one minute in milliseconds
            final long minuteInMs = 60*1000;

            // set up task to run every minute
            timer.scheduleAtFixedRate(new MinuteTick(this),
                    minuteInMs, minuteInMs);
        }   
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
        }
    }
}
