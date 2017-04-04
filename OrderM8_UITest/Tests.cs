using System;
using System.IO;
using System.Linq;
using NUnit.Framework;
using Xamarin.UITest;
using Xamarin.UITest.Queries;

namespace OrderM8_UITest
{
    [TestFixture(Platform.Android)]
    //[TestFixture(Platform.iOS)]
    public class Tests
    {
        IApp app;
        Platform platform;

        public Tests(Platform platform)
        {
            this.platform = platform;
        }

        [SetUp]
        public void BeforeEachTest()
        {
            app = AppInitializer.StartApp(platform);
        }

        //[Ignore]
        [Test]
        public void AppLaunches()
        {
            app.Screenshot("First screen.");
        }

        #region Login Tests

        //[Ignore]
        [Test]
        [Category("Login")]
        public void LoginCorrect()
        {
            app.EnterText("txtUsername", "wat");
            app.EnterText("txtPassword", "wat");

            app.DismissKeyboard();
            app.Tap("btnLogin");
        }

        //[Ignore]
        [Test]
        [Category("Login")]
        public void LoginIncorrect()
        {
            app.EnterText("txtUsername", "wat");
            app.EnterText("txtPassword", "watf");

            app.DismissKeyboard();
            app.Tap("btnLogin");

            app.WaitForElement("Error logging in", "No errors found", TimeSpan.FromSeconds(5));

            var result = app.Query("Error logging in");
            Assert.IsTrue(result.Any(), "invalid login test failed");
        }

        #endregion 


        //[Ignore]
        [Test]
        public void SelectTable()
        {
            //Login
            app.EnterText("txtUsername", "wat");
            app.EnterText("txtPassword", "wat");

            app.DismissKeyboard();
            app.Tap("btnLogin");

            app.Tap("0");

            var result = app.Query("Add");
            Assert.IsTrue(result.Any(), "Cannot open table");
        }

        //[Ignore]
        [Test]
        public void AddOrderItems()
        {
            //Login with Credentials
            app.EnterText("txtUsername", "wat");
            app.EnterText("txtPassword", "wat");

            app.DismissKeyboard();
            app.Tap("btnLogin");

            //select the table
            app.Tap("0");

            //wait for displaying the next view
            app.WaitForElement("Add", "Error while opening the table", TimeSpan.FromSeconds(5));
            
            //open next view
            app.Tap("Add");
            app.WaitForElement("Neue Bestellung", "Bestell-View konnte nicht aufgerufen werden", TimeSpan.FromSeconds(5));

            //add Schopf
            app.Tap("Schopf");
            app.EnterText("Notiz", "mit Senf");
            app.Tap("Add");
            app.WaitForElement("-", "Order is not added", TimeSpan.FromSeconds(5));

            //add Schnitzel
            app.Tap("Add");
            app.WaitForElement("Neue Bestellung", "Bestell-View konnte nicht aufgerufen werden", TimeSpan.FromSeconds(5));
            app.Tap("Schnitzel");
            app.ClearText("Notiz");
            app.EnterText("Notiz", "mit Ketchup");
            app.Tap("Add");
            app.WaitForElement("-", "Order is not added", TimeSpan.FromSeconds(5));

            app.Back();
        }

        //[Ignore]
        [Test]
        public void PayItems()
        {

            //Login with Credentials
            app.EnterText("txtUsername", "wat");
            app.EnterText("txtPassword", "wat");

            app.DismissKeyboard();
            app.Tap("btnLogin");

            //select the table
            app.Tap("0");

            //wait for displaying the next view
            app.WaitForElement("Bill", "Error while opening the table", TimeSpan.FromSeconds(5));

            var result = app.Query("Bill");
            Assert.IsTrue(result.Any(), "Cannot open order view");

            //open next view
            app.Tap("-");
            app.Tap("-");

            result = app.Query("-");
            Assert.IsTrue(result.Length == 0, "Cannot open pay view");

            app.Tap("Bill");
            app.WaitForElement("Pay", "Pay View konnte nicht geöffnet werden", TimeSpan.FromSeconds(5));

            result = app.Query("Pay");
            Assert.IsTrue(result.Any(), "Cannot open pay view");

            app.Tap("Pay");
            app.WaitForElement("Bill", "Switch back error", TimeSpan.FromSeconds(5));

            result = app.Query("Bill");
            Assert.IsTrue(result.Any(), "Cannot open order view");

            app.Back();
        }

        //[Ignore]
        [Test]
        public void PickerValueChange()
        {
            //Login with Credentials
            app.EnterText("txtUsername", "wat");
            app.EnterText("txtPassword", "wat");

            app.DismissKeyboard();
            app.Tap("btnLogin");

            //select the table
            app.Tap("0");

            //wait for displaying the next view
            app.WaitForElement("Add", "Error while opening the table", TimeSpan.FromSeconds(5));

            //open next view
            app.Tap("Add");
            app.WaitForElement("Neue Bestellung", "Bestell-View konnte nicht aufgerufen werden", TimeSpan.FromSeconds(5));

            //open picker
            app.Tap("Food");

            app.Tap(query => query.All().Marked("Drink"));

            app.Tap("OK");

            app.Tap("Cola");

            var result = app.Query("Cola");
            Assert.IsTrue(result.Length == 2, "Cola label does not appear");
        }

        //[Ignore]
        [Test]
        public void PayTwoItemsSeparate()
        {
            //Login with Credentials
            app.EnterText("txtUsername", "wat");
            app.EnterText("txtPassword", "wat");

            app.DismissKeyboard();
            app.Tap("btnLogin");

            //select the table
            app.Tap("0");

            //wait for displaying the next view
            app.WaitForElement("Bill", "Error while opening the table", TimeSpan.FromSeconds(5));

            var result = app.Query("Bill");
            Assert.IsTrue(result.Any(), "Cannot open order view");

            //select the first item
            app.Tap("-");

            app.Tap("Bill");
            app.WaitForElement("Pay", "Pay View konnte nicht geöffnet werden", TimeSpan.FromSeconds(5));

            result = app.Query("Pay");
            Assert.IsTrue(result.Any(), "Cannot open pay view");

            //pay the first item
            app.Tap("Pay");
            app.WaitForElement("Bill", "Switch back error", TimeSpan.FromSeconds(5));

            //select the second item
            app.Tap("-");

            app.Tap("Bill");
            app.WaitForElement("Pay", "Pay View konnte nicht geöffnet werden", TimeSpan.FromSeconds(5));

            result = app.Query("Pay");
            Assert.IsTrue(result.Any(), "Cannot open pay view");

            //pay the second item
            app.Tap("Pay");
            app.WaitForElement("Bill", "Switch back error", TimeSpan.FromSeconds(5));

            result = app.Query("Bill");
            Assert.IsTrue(result.Any(), "Cannot open order view");

            //Go back to the main menu
            app.Back();
        }
    }
}

