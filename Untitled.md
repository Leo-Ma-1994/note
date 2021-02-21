getprop sys.boot_completed







  String boot = SystemProperties.get("sys.boot_completed");
    Log.i("GlobalApplication", "boot : " + boot);
    if ("1".equals(boot)){

