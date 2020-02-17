package Demo;

import com.arcsoft.face.*;
import com.arcsoft.face.enums.*;
import com.arcsoft.face.toolkit.ImageInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.arcsoft.face.toolkit.ImageInfoEx;

import static com.arcsoft.face.toolkit.ImageFactory.getGrayData;
import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;


public class FaceEngineTest {


    public static void main(String[] args) {

        //????????
        String appId = "9Sf3fLtCweqTGAhuNdbZRZdZZeroD6VHwxqszhC4wzzf";
        String sdkKey = "E718szYnWJrunycXd22we64vF9AHff4oUh9LBcoFk54L";


        FaceEngine faceEngine = new FaceEngine("F:\\HKUST\\MSC_Project\\v3-windows\\ArcsoftVersion3-1\\lib\\WIN64");
        //????????
        int errorCode = faceEngine.activeOnline(appId, sdkKey);

        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("???‰Ì?????");
        }


        ActiveFileInfo activeFileInfo=new ActiveFileInfo();
        errorCode = faceEngine.getActiveFileInfo(activeFileInfo);
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("????????????????");
        }

        //????????
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
        engineConfiguration.setDetectFaceMaxNum(10);
        engineConfiguration.setDetectFaceScaleVal(16);
        //????????
        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportAge(true);
        functionConfiguration.setSupportFace3dAngle(true);
        functionConfiguration.setSupportFaceDetect(true);
        functionConfiguration.setSupportFaceRecognition(true);
        functionConfiguration.setSupportGender(true);
        functionConfiguration.setSupportLiveness(true);
        functionConfiguration.setSupportIRLiveness(true);
        engineConfiguration.setFunctionConfiguration(functionConfiguration);


        //?????????
        errorCode = faceEngine.init(engineConfiguration);

        if (errorCode != ErrorInfo.MOK.getValue()) {
            System.out.println("????????????");
        }


        //???????
        ImageInfo imageInfo = getRGBData(new File("pictures/nba/kobe.jpg"));
        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
        errorCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
        System.out.println(faceInfoList);

        //???????
        FaceFeature faceFeature = new FaceFeature();
        errorCode = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);
        System.out.println("???????ß≥??" + faceFeature.getFeatureData().length);

        //???????2
        ImageInfo imageInfo2 = getRGBData(new File("pictures/nba/kobe and lebron1.jpg"));
        List<FaceInfo> faceInfoList2 = new ArrayList<FaceInfo>();
        errorCode = faceEngine.detectFaces(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(),imageInfo.getImageFormat(), faceInfoList2);
        System.out.println(faceInfoList);

        //???????2
        FaceFeature faceFeature2 = new FaceFeature();
        FaceFeature faceFeature3 = new FaceFeature();
        errorCode = faceEngine.extractFaceFeature(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(), imageInfo.getImageFormat(), faceInfoList2.get(0), faceFeature2);
        errorCode = faceEngine.extractFaceFeature(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(), imageInfo.getImageFormat(), faceInfoList2.get(1), faceFeature3);
        System.out.println("?????1??ß≥??" + faceFeature.getFeatureData().length);
        System.out.println("?????2??ß≥??" + faceFeature.getFeatureData().length);

        //???????
        FaceFeature targetFaceFeature = new FaceFeature();
        targetFaceFeature.setFeatureData(faceFeature.getFeatureData());

        FaceFeature sourceFaceFeature1 = new FaceFeature();
        sourceFaceFeature1.setFeatureData(faceFeature2.getFeatureData());

        FaceFeature sourceFaceFeature2 = new FaceFeature();
        sourceFaceFeature2.setFeatureData(faceFeature3.getFeatureData());

        FaceSimilar faceSimilar = new FaceSimilar();
        FaceSimilar faceSimilar2 = new FaceSimilar();

        errorCode = faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature1, faceSimilar);
        errorCode = faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature2, faceSimilar2);

        System.out.println("?1??????" + faceSimilar.getScore());
        System.out.println("?2??????" + faceSimilar2.getScore());

        if(faceSimilar.getScore() > faceSimilar2.getScore()){
            System.out.println("?1????");
        }
        else System.out.println("?2????");









        //??????????
        errorCode = faceEngine.setLivenessParam(0.5f, 0.7f);
        //??????????
        FunctionConfiguration configuration = new FunctionConfiguration();
        configuration.setSupportAge(true);
        configuration.setSupportFace3dAngle(true);
        configuration.setSupportGender(true);
        configuration.setSupportLiveness(true);
        errorCode = faceEngine.process(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList, configuration);


        //?????
        List<GenderInfo> genderInfoList = new ArrayList<GenderInfo>();
        errorCode = faceEngine.getGender(genderInfoList);
        System.out.println("???" + genderInfoList.get(0).getGender());

        //??????
        List<AgeInfo> ageInfoList = new ArrayList<AgeInfo>();
        errorCode = faceEngine.getAge(ageInfoList);
        System.out.println("????" + ageInfoList.get(0).getAge());

        //3D??????
        List<Face3DAngle> face3DAngleList = new ArrayList<Face3DAngle>();
        errorCode = faceEngine.getFace3DAngle(face3DAngleList);
        System.out.println("3D????" + face3DAngleList.get(0).getPitch() + "," + face3DAngleList.get(0).getRoll() + "," + face3DAngleList.get(0).getYaw());

        //??????
        List<LivenessInfo> livenessInfoList = new ArrayList<LivenessInfo>();
        errorCode = faceEngine.getLiveness(livenessInfoList);
        System.out.println("????" + livenessInfoList.get(0).getLiveness());


        //IR???????
        ImageInfo imageInfoGray = getGrayData(new File("d:\\IR_480p.jpg"));
        List<FaceInfo> faceInfoListGray = new ArrayList<FaceInfo>();
        errorCode = faceEngine.detectFaces(imageInfoGray.getImageData(), imageInfoGray.getWidth(), imageInfoGray.getHeight(), imageInfoGray.getImageFormat(), faceInfoListGray);

        FunctionConfiguration configuration2 = new FunctionConfiguration();
        configuration2.setSupportIRLiveness(true);
        errorCode = faceEngine.processIr(imageInfoGray.getImageData(), imageInfoGray.getWidth(), imageInfoGray.getHeight(), imageInfoGray.getImageFormat(), faceInfoListGray, configuration2);
        //IR??????
        List<IrLivenessInfo> irLivenessInfo = new ArrayList<>();
        errorCode = faceEngine.getLivenessIr(irLivenessInfo);
        System.out.println("IR????" + irLivenessInfo.get(0).getLiveness());

        ImageInfoEx imageInfoEx = new ImageInfoEx();
        imageInfoEx.setHeight(imageInfo.getHeight());
        imageInfoEx.setWidth(imageInfo.getWidth());
        imageInfoEx.setImageFormat(imageInfo.getImageFormat());
        imageInfoEx.setImageDataPlanes(new byte[][]{imageInfo.getImageData()});
        imageInfoEx.setImageStrides(new int[]{imageInfo.getWidth() * 3});
        List<FaceInfo> faceInfoList1 = new ArrayList<>();
        errorCode = faceEngine.detectFaces(imageInfoEx, DetectModel.ASF_DETECT_MODEL_RGB, faceInfoList1);

        FunctionConfiguration fun = new FunctionConfiguration();
        fun.setSupportAge(true);
        errorCode = faceEngine.process(imageInfoEx, faceInfoList1, functionConfiguration);
        List<AgeInfo> ageInfoList1 = new ArrayList<>();
        int age = faceEngine.getAge(ageInfoList1);
        System.out.println("????" + ageInfoList1.get(0).getAge());

        FaceFeature feature = new FaceFeature();
        errorCode = faceEngine.extractFaceFeature(imageInfoEx, faceInfoList1.get(0), feature);


        //????ßÿ??
        errorCode = faceEngine.unInit();
    }
}