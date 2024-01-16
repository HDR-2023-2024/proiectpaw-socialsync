/********************************************************************************
** Form generated from reading UI file 'resourcedetails.ui'
**
** Created by: Qt User Interface Compiler version 6.6.1
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_RESOURCEDETAILS_H
#define UI_RESOURCEDETAILS_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QDialog>
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QLabel>
#include <QtWidgets/QListView>
#include <QtWidgets/QSpacerItem>
#include <QtWidgets/QVBoxLayout>
#include "custompushbutton.h"

QT_BEGIN_NAMESPACE

class Ui_ResourceDetails
{
public:
    QVBoxLayout *verticalLayout_2;
    QVBoxLayout *verticalLayout;
    QLabel *label;
    QListView *listView;
    QSpacerItem *horizontalSpacer;
    QHBoxLayout *horizontalLayout;
    QSpacerItem *horizontalSpacer_3;
    CustomPushButton *deleteBtn;
    QSpacerItem *horizontalSpacer_2;

    void setupUi(QDialog *ResourceDetails)
    {
        if (ResourceDetails->objectName().isEmpty())
            ResourceDetails->setObjectName("ResourceDetails");
        ResourceDetails->resize(540, 569);
        verticalLayout_2 = new QVBoxLayout(ResourceDetails);
        verticalLayout_2->setObjectName("verticalLayout_2");
        verticalLayout = new QVBoxLayout();
        verticalLayout->setObjectName("verticalLayout");
        label = new QLabel(ResourceDetails);
        label->setObjectName("label");
        QFont font;
        font.setPointSize(16);
        font.setBold(true);
        label->setFont(font);
        label->setAlignment(Qt::AlignCenter);

        verticalLayout->addWidget(label);

        listView = new QListView(ResourceDetails);
        listView->setObjectName("listView");

        verticalLayout->addWidget(listView);

        horizontalSpacer = new QSpacerItem(40, 20, QSizePolicy::Expanding, QSizePolicy::Minimum);

        verticalLayout->addItem(horizontalSpacer);

        horizontalLayout = new QHBoxLayout();
        horizontalLayout->setObjectName("horizontalLayout");
        horizontalSpacer_3 = new QSpacerItem(40, 20, QSizePolicy::Expanding, QSizePolicy::Minimum);

        horizontalLayout->addItem(horizontalSpacer_3);

        deleteBtn = new CustomPushButton(ResourceDetails);
        deleteBtn->setObjectName("deleteBtn");

        horizontalLayout->addWidget(deleteBtn);

        horizontalSpacer_2 = new QSpacerItem(40, 20, QSizePolicy::Expanding, QSizePolicy::Minimum);

        horizontalLayout->addItem(horizontalSpacer_2);


        verticalLayout->addLayout(horizontalLayout);


        verticalLayout_2->addLayout(verticalLayout);


        retranslateUi(ResourceDetails);

        QMetaObject::connectSlotsByName(ResourceDetails);
    } // setupUi

    void retranslateUi(QDialog *ResourceDetails)
    {
        ResourceDetails->setWindowTitle(QCoreApplication::translate("ResourceDetails", "Resource Details", nullptr));
        label->setText(QCoreApplication::translate("ResourceDetails", "Details", nullptr));
        deleteBtn->setText(QCoreApplication::translate("ResourceDetails", "Delete", nullptr));
    } // retranslateUi

};

namespace Ui {
    class ResourceDetails: public Ui_ResourceDetails {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_RESOURCEDETAILS_H
