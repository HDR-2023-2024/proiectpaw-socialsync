#include "loginform.h"

#include <QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    LogInForm logInForm;
    logInForm.show();
    return a.exec();
}
