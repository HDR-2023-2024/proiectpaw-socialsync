#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QNetworkAccessManager>
#include <QNetworkReply>
#include <resourcepanel.h>

QT_BEGIN_NAMESPACE
namespace Ui {
class MainWindow;
}
QT_END_NAMESPACE

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr, QString token = "Bearer ");
    ~MainWindow();

private slots:
    void on_topicsButton_clicked();
    void response_received(QNetworkReply *reply);

    void on_usersButton_clicked();

    void on_postsButton_clicked();

    void on_commButton_clicked();

private:
    Ui::MainWindow *ui;
    ResourcePanel *resourcePanel;
    QNetworkAccessManager *manager;
    QNetworkRequest request;
    // QString topicsURL = "http://localhost:8084/api/v1/topics";
    // QString usersURL = "http://localhost:8082/api/v1/users";
    // QString postsURL = "http://localhost:8080/api/v1/posts";
    // QString commentsURL = "http://localhost:8081/api/v1/comments";
    QString topicsURL = "http://localhost:8086/api/v1/topics";
    QString usersURL = "http://localhost:8086/api/v1/users";
    QString postsURL = "http://localhost:8086/api/v1/posts";
    QString commentsURL = "http://localhost:8086/api/v1/comments";
};
#endif // MAINWINDOW_H
