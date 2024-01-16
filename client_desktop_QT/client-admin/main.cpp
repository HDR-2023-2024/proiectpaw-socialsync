#include "loginform.h"

#include <QApplication>
#include <QFile>
#include <QFileInfo>

int main(int argc, char *argv[])
{
    QString fileName = ":/style/styles.css";
    QFileInfo info(fileName);
    QFile file(info.absoluteFilePath());

    if (!file.open(QIODevice::ReadOnly | QIODevice::Text))
    {
        qDebug() << "Could not open file";
        return -1;
    }

    QTextStream in(&file);
    QString styleSheet = in.readAll();

    file.close();

    QApplication a(argc, argv);
    a.setStyleSheet(styleSheet);

    LogInForm logInForm;
    logInForm.show();
    return a.exec();
}
