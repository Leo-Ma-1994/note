​    private void startBootstrapServices() {



​        traceBeginAndSlog("StartLedService");

​        try {

​            Slog.i("Led", "StartLedService");

​            ServiceManager.addService(Context.LED_SERVICE, new LedService(mSystemContext));



​        } catch (Throwable e) {

​            reportWtf("starting Led Service", e);

​        }

​        traceEnd();





















