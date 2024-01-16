#include "loginform.h"
#include "ui_loginform.h"

#include <mainwindow.h>

LogInForm::LogInForm(QWidget *parent)
    : QDialog(parent)
    , ui(new Ui::LogInForm)
{
    ui->setupUi(this);
    ui->lineEditPassword->setEchoMode(QLineEdit::Password);
    ui->credentialMsg->setWordWrap(true);

    manager = new QNetworkAccessManager(this);
    connect(manager, &QNetworkAccessManager::finished, this, &LogInForm::response_received);
}

LogInForm::~LogInForm()
{
    delete ui;
}

void LogInForm::on_btnLogIn_clicked()
{
    QString username = ui->lineEditUsername->text();
    QString password = ui->lineEditPassword->text();

    request.setUrl(QUrl(loginURL));
    request.setHeader(QNetworkRequest::ContentTypeHeader, "application/json");

    QJsonObject json;
    json.insert("username", username);
    json.insert("password", password);

    QJsonDocument doc(json);
    QByteArray postData = doc.toJson();
    request.setRawHeader("Authorization", "");

    manager->post(request, postData);
}

void LogInForm::response_received(QNetworkReply *reply)
{
    QString msg = QString::fromUtf8(reply->readAll());
    //qDebug() << msg;

    QString statusCode = reply->attribute(QNetworkRequest::HttpStatusCodeAttribute).toString();

    //qDebug() << statusCode;

    QString token = QJsonDocument::fromJson(msg.toUtf8()).object().value("token").toString();

    qDebug() << token;

    if (statusCode == "200")
    {
        MainWindow *w = new MainWindow(nullptr, token);
        w->show();
        this->hide();
    }
    else
    {
        ui->credentialMsg->setText(msg);
    }
}

