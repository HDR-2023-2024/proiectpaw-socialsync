#ifndef RESOURCEDETAILS_H
#define RESOURCEDETAILS_H

#include <QDialog>
#include <QJsonObject>
#include <QStandardItemModel>
#include <QAbstractItemView>
#include <QCloseEvent>
#include <qnetworkaccessmanager.h>

namespace Ui {
class ResourceDetails;
}

class ResourceDetails : public QDialog
{
    Q_OBJECT

public:
    explicit ResourceDetails(QWidget *parent = nullptr);
    ~ResourceDetails();

    void setDetails(QJsonValue value);
    void setUrl(QString url);
    void setToken(QString token);

protected:
    void closeEvent(QCloseEvent *event) override {
        model->clear();
        QDialog::closeEvent(event);
    }

private slots:
    void on_deleteBtn_clicked();
    void response_received(QNetworkReply *reply);

private:
    Ui::ResourceDetails *ui;
    QJsonValue details;
    QStandardItemModel *model;
    QNetworkAccessManager *manager;
    QNetworkRequest request;
    QString url;
    QString token;
};

#endif // RESOURCEDETAILS_H
