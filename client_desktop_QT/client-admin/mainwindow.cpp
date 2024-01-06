#include "mainwindow.h"
#include "ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent, QString token)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    resourcePanel = new ResourcePanel();
    request.setRawHeader("Authorization", token.toUtf8());

    manager = new QNetworkAccessManager(this);
    connect(manager, &QNetworkAccessManager::finished, this, &MainWindow::response_received);
    // request.setRawHeader(QByteArray("X-User-Role"), QByteArray("admin"));
    // request.setRawHeader(QByteArray("X-User-Id"), QByteArray("bongola bongo cia cia cia"));
}

MainWindow::~MainWindow()
{
    delete ui;
    delete manager;
}

void MainWindow::on_usersButton_clicked()
{
    request.setUrl(QUrl(usersURL));
    manager->get(request);
    resourcePanel->setTitle("Users");
    resourcePanel->show();
}

void MainWindow::on_topicsButton_clicked()
{
    request.setUrl(QUrl(topicsURL));
    manager->get(request);
    resourcePanel->setTitle("Topics");
    resourcePanel->show();
}

void MainWindow::on_postsButton_clicked()
{
    request.setUrl(QUrl(postsURL));
    manager->get(request);
    resourcePanel->setTitle("Posts");
    resourcePanel->show();
}


void MainWindow::on_commButton_clicked()
{
    request.setUrl(QUrl(commentsURL));
    manager->get(request);
    resourcePanel->setTitle("Comments");
    resourcePanel->show();
}

void MainWindow::response_received(QNetworkReply *reply)
{
    if (reply->error()) {
        qDebug() << reply->errorString();
        return;
    }

    QString response = reply->readAll();
    qDebug() << response;
    resourcePanel->setContent(response);
}

