#ifndef LOGINFORM_H
#define LOGINFORM_H

#include <QDialog>
#include <qnetworkaccessmanager.h>

namespace Ui {
class LogInForm;
}

class LogInForm : public QDialog
{
    Q_OBJECT

public:
    explicit LogInForm(QWidget *parent = nullptr);
    ~LogInForm();

private slots:
    void on_btnLogIn_clicked();
    void response_received(QNetworkReply *reply);

private:
    Ui::LogInForm *ui;
    QString loginURL = "http://localhost:8082/api/v1/users/login";
    QNetworkAccessManager *manager;
    QNetworkRequest request;
};

#endif // LOGINFORM_H
