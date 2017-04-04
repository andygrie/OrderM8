using System;
using System.IO;
using System.Linq;
using Xamarin.UITest;
using Xamarin.UITest.Queries;

namespace OrderM8_UITest
{
    public class AppInitializer
    {
        public static IApp StartApp(Platform platform)
        {
            if (platform == Platform.Android)
            {
                return ConfigureApp
                    .Android
                    .ApkFile("C:/Users/lukas/Documents/GitHub/OrderM8/OrderM8_mvvm/OrderM8_mvvm/OrderM8_mvvm.Droid/bin/Release/OrderM8_mvvm.Droid.apk")
                    .StartApp();
            }

            return ConfigureApp
                .iOS
                .StartApp();
        }
    }
}

