#include "resourcedetails.h"
#include "ui_resourcedetails.h"

ResourceDetails::ResourceDetails(QWidget *parent)
    : QDialog(parent)
    , ui(new Ui::ResourceDetails)
{
    ui->setupUi(this);
    ui->listView->setEditTriggers(QAbstractItemView::NoEditTriggers);
    ui->listView->setWordWrap(true);

    model = new QStandardItemModel(this);
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
        QStandardItem *item = new QStandardItem(key + " : " + valueString);
        model->appendRow(item);
    }
    ui->listView->setModel(model);
}
