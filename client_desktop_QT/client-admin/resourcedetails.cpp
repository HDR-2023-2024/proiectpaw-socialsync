#include "resourcedetails.h"
#include "qnetworkreply.h"
#include "ui_resourcedetails.h"

#include <error.h>

ResourceDetails::ResourceDetails(QWidget *parent)
    : QDialog(parent)
    , ui(new Ui::ResourceDetails)
{
    ui->setupUi(this);
    ui->listView->setEditTriggers(QAbstractItemView::NoEditTriggers);
    ui->listView->setWordWrap(true);

    model = new QStandardItemModel(this);

    manager = new QNetworkAccessManager(this);
    connect(manager, &QNetworkAccessManager::finished, this, &ResourceDetails::response_received);
}

ResourceDetails::~ResourceDetails()
{
    delete ui;
    delete model;
}

void ResourceDetails::setDetails(QJsonValue value)
{
    this->details = value;
    QJsonObject json = value.toObject();
    foreach(const QString& key, json.keys()) {
        QJsonValue value = json.value(key);
        QString valueString = value.isString() ? value.toString() : QString::number(value.toInt());
        QStandardItem *item = new QStandardItem(key.toUpper() + " : " + valueString);
        model->appendRow(item);
    }
    ui->listView->setModel(model);
}

void ResourceDetails::setUrl(QString url)
{
    this->url = url;
}

void ResourceDetails::setToken(QString token)
{
    this->token = token;
}

void ResourceDetails::on_deleteBtn_clicked()
{
    QString id = this->details.toObject().value("id").toString();
    request.setUrl(QUrl(url + "/" + id));
    request.setRawHeader(QByteArray("Authorization"), token.toUtf8());
    manager->deleteResource(request);
}

void ResourceDetails::response_received(QNetworkReply *reply)
{
    if (reply->error()) {
        qDebug() << reply->errorString();
        return;
    }

    QString response = reply->readAll();
    qDebug() << response;
}

