package dongzhong.testforfloatingwindow;

import android.app.Application;

public class MyApplication extends Application {
    private boolean isShow = false;

    private MyListener myListener;
    public void setVisibity(Boolean visibity){
        isShow = visibity;
        //TODO
        notifyIsShow();

    }

    private void notifyIsShow(){
        myListener.onIsShowChanged(isShow);
    }

    public interface MyListener{
        void onIsShowChanged(boolean isShow);
    }

}
