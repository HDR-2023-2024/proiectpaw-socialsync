#include "mainwindow.h"
#include "ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent, QString token)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    ui->listView->setEditTriggers(QAbstractItemView::NoEditTriggers);
    ui->listView->setWordWrap(true);

    qDebug() << token;

    this->token = token;

    request.setRawHeader(QByteArray("Authorization"), token.toUtf8());
    model = new QStandardItemModel(this);
    manager = new QNetworkAccessManager(this);
    connect(manager, &QNetworkAccessManager::finished, this, &MainWindow::response_received);

    resourceDetails = new ResourceDetails(this);
}

MainWindow::~MainWindow()
{
    delete ui;
    delete manager;
}

void MainWindow::on_usersButton_clicked()
{
    request.setUrl(QUrl(usersURL));
    selectedURL = usersURL;
    manager->get(request);
    ui->resLabel->setText("Users");
    model->clear();
}

void MainWindow::on_topicsButton_clicked()
{
    request.setUrl(QUrl(topicsURL));
    selectedURL = topicsURL;
    manager->get(request);
    ui->resLabel->setText("Topics");
    model->clear();
}

void MainWindow::on_postsButton_clicked()
{
    request.setUrl(QUrl(postsURL));
    selectedURL = postsURL;
    manager->get(request);
    ui->resLabel->setText("Posts");
    model->clear();
}


void MainWindow::on_commButton_clicked()
{
    request.setUrl(QUrl(commentsURL));
    selectedURL = commentsURL;
    manager->get(request);
    ui->resLabel->setText("Comments");
    model->clear();
}

void MainWindow::response_received(QNetworkReply *reply)
{
    if (reply->error()) {
        qDebug() << reply->errorString();
        return;
    }

    QString response = reply->readAll();
    qDebug() << response;

    QString resourceName = "id";

    if(ui->resLabel->text() == "Users")
        resourceName = "username";
    if(ui->resLabel->text() == "Topics")
        resourceName = "name";
    if(ui->resLabel->text() == "Posts")
        resourceName = "title";
    if(ui->resLabel->text() == "Comments")
        resourceName = "content";

    this->json = QJsonDocument::fromJson(response.toUtf8()).object();
    foreach(const QString& key, json.keys()) {
        QJsonValue value = json.value(key);
        QStandardItem *item = new QStandardItem(key + "#" + value[resourceName].toString());
        model->appendRow(item);
    }
    ui->listView->setModel(model);

    //resourcePanel->setContent(response);
}


void MainWindow::on_listView_doubleClicked(const QModelIndex &index)
{
    QString rawData = index.data().toString();
    QString id = rawData.section('#', 0, 0);

    QJsonValue data = this->json.value(id);
    resourceDetails->setDetails(data);
    resourceDetails->setUrl(selectedURL);
    resourceDetails->setToken(token);
    resourceDetails->show();
}

