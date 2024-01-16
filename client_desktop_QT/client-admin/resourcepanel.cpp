#include "resourcepanel.h"
#include "ui_resourcepanel.h"

ResourcePanel::ResourcePanel(QWidget *parent)
    : QDialog(parent)
    , ui(new Ui::ResourcePanel)
{
    ui->setupUi(this);
    ui->listView->setEditTriggers(QAbstractItemView::NoEditTriggers);

    model = new QStandardItemModel(this);
    resourceDetails = new ResourceDetails(this);
}

ResourcePanel::~ResourcePanel()
{
    delete ui;
    delete model;
}

void ResourcePanel::setTitle(QString title)
{
    ui->panelTitle->setText(title);
    if(title == "Users")
        resourceName = "username";
    if(title == "Topics")
        resourceName = "name";
    if(title == "Posts")
        resourceName = "title";
    if(title == "Comments")
        resourceName = "content";
}

void ResourcePanel::setContent(QString content)
{
    this->content = QJsonDocument::fromJson(content.toUtf8()).object();
    QJsonObject json = this->content;
    foreach(const QString& key, json.keys()) {
        QJsonValue value = json.value(key);
        QStandardItem *item = new QStandardItem(key + "#" + value[resourceName].toString());
        model->appendRow(item);
    }
    ui->listView->setModel(model);
}

void ResourcePanel::on_listView_doubleClicked(const QModelIndex &index)
{
    QString rawData = index.data().toString();
    QString id = rawData.section('#', 0, 0);

    QJsonValue data = this->content.value(id);
    resourceDetails->setDetails(data);
    resourceDetails->setUrl(url);
    resourceDetails->setToken(token);
    resourceDetails->show();
}

void ResourcePanel::setUrl(QString url)
{
    this->url = url;
}

void ResourcePanel::setToken(QString token)
{
    this->token = token;
}
